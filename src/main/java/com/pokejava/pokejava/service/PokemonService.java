package com.pokejava.pokejava.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final WebClient webClient;
    private static final Logger logger = Logger.getLogger(PokemonService.class.getName());

    public PokemonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://pokeapi.co/api/v2").build();
    }

    public PokemonResponse fetchPokemonData(int id) {
        // 포켓몬 기본 정보 가져오기
        Mono<PokemonApiResponse> pokemonMono = this.webClient.get()
                .uri("/pokemon/{id}", id)
                .retrieve()
                .bodyToMono(PokemonApiResponse.class);

        // 포켓몬 종 정보 가져오기
        Mono<PokemonSpeciesApiResponse> speciesMono = this.webClient.get()
                .uri("/pokemon-species/{id}", id)
                .retrieve()
                .bodyToMono(PokemonSpeciesApiResponse.class);

        // 두 개의 Mono를 결합하여 결과를 가져오기
        return Mono.zip(pokemonMono, speciesMono)
                .flatMap(tuple -> {
                    PokemonApiResponse pokemon = tuple.getT1();
                    PokemonSpeciesApiResponse species = tuple.getT2();

                    // 한글 이름 가져오기
                    String koreanName = species.getNames().stream()
                            .filter(name -> "ko".equals(name.getLanguage().getName()))
                            .map(PokemonSpeciesApiResponse.Name::getName)
                            .findFirst()
                            .orElse("이름 없음"); // 기본값 설정

                    // ID를 기반으로 이미지 URL 생성
                    String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";

                    // 키와 무게 가져오기
                    int height = pokemon.getHeight();
                    int weight = pokemon.getWeight();

                    // 속성과 특성을 한국어로 가져오기
                    Mono<List<String>> typesMono = fetchKoreanNames(pokemon.getTypes().stream()
                            .map(type -> type.getType().getUrl())
                            .collect(Collectors.toList()));

                    Mono<List<String>> abilitiesMono = fetchKoreanNames(pokemon.getAbilities().stream()
                            .map(ability -> ability.getAbility().getUrl())
                            .collect(Collectors.toList()));

                    return Mono.zip(typesMono, abilitiesMono)
                            .map(namesTuple -> {
                                List<String> types = namesTuple.getT1();
                                List<String> abilities = namesTuple.getT2();

                                logger.info("Fetched Pokemon (Korean): " + koreanName);
                                logger.info("Constructed Image URL: " + imageUrl);

                                return new PokemonResponse(koreanName, imageUrl, height, weight, types, abilities);
                            });
                })
                .block(); // 블록킹 호출로 결과를 반환
    }

    private Mono<List<String>> fetchKoreanNames(List<String> urls) {
        return Flux.fromIterable(urls)
                .flatMap(url -> this.webClient.get().uri(url)
                        .retrieve()
                        .bodyToMono(NamedApiResource.class)
                        .map(resource -> resource.getNames().stream()
                                .filter(name -> "ko".equals(name.getLanguage().getName()))
                                .map(NamedApiResource.Name::getName)
                                .findFirst()
                                .orElse("이름 없음"))) // 기본값 설정
                .collectList();
    }

    // 포켓몬 기본 정보 응답 DTO
    static class PokemonApiResponse {
        private int height; // 키
        private int weight; // 무게
        private List<Type> types; // 속성
        private List<Ability> abilities; // 특성

        public int getHeight() {
            return height;
        }

        public int getWeight() {
            return weight;
        }

        public List<Type> getTypes() {
            return types;
        }

        public List<Ability> getAbilities() {
            return abilities;
        }

        static class Type {
            private TypeDetail type;

            public TypeDetail getType() {
                return type;
            }

            static class TypeDetail {
                private String url;

                public String getUrl() {
                    return url;
                }
            }
        }

        static class Ability {
            private AbilityDetail ability;

            public AbilityDetail getAbility() {
                return ability;
            }

            static class AbilityDetail {
                private String url;

                public String getUrl() {
                    return url;
                }
            }
        }
    }

    // 포켓몬 종 정보 응답 DTO
    static class PokemonSpeciesApiResponse {
        private List<Name> names;

        public List<Name> getNames() {
            return names;
        }

        static class Name {
            private String name;
            private Language language;

            public String getName() {
                return name;
            }

            public Language getLanguage() {
                return language;
            }

            static class Language {
                private String name;

                public String getName() {
                    return name;
                }
            }
        }
    }

    // 이름과 URL을 포함하는 API 리소스 DTO
    static class NamedApiResource {
        private List<Name> names;

        public List<Name> getNames() {
            return names;
        }

        static class Name {
            private String name;
            private Language language;

            public String getName() {
                return name;
            }

            public Language getLanguage() {
                return language;
            }

            static class Language {
                private String name;

                public String getName() {
                    return name;
                }
            }
        }
    }

    // 포켓몬 정보를 담는 DTO 클래스
    public static class PokemonResponse {
        private final String name;     // 한국어 이름
        private final String imageUrl; // 이미지 URL
        private final int height;       // 키
        private final int weight;       // 무게
        private final List<String> types; // 속성 목록
        private final List<String> abilities; // 특성 목록

        public PokemonResponse(String name, String imageUrl, int height, int weight, List<String> types, List<String> abilities) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.height = height;
            this.weight = weight;
            this.types = types;
            this.abilities = abilities;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getHeight() {
            return height;
        }

        public int getWeight() {
            return weight;
        }

        public List<String> getTypes() {
            return types;
        }

        public List<String> getAbilities() {
            return abilities;
        }
    }
}
