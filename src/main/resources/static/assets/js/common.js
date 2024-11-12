const common = {
    defaultSetup: function(){
        
    },
    showView: function(viewId) {
        // 모든 .sideView 요소 숨기기
        $('.sideView').removeClass('show').addClass('hideView');

        // 선택한 viewId 요소를 화면에 보이게 하기
        $(`#${viewId}`).removeClass('hideView').addClass('show');
    }
};

$(function(){
    common.defaultSetup();
});