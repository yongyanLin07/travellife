$(function() {

    $('#da-slider').cslider();
    var filterList = {

        init: function () {

            // MixItUp plugin
            // http//mixitup.io
            $('#portfoliolist').mixitup({
                targetSelector: '.portfolio',
                filterSelector: '.filter',
                effects: ['fade'],
                easing: 'snap',
                // call the hover effect
                onMixEnd: filterList.hoverEffect()
            });

        },

        hoverEffect: function () {

            // Simple parallax effect
            $('#portfoliolist .portfolio').hover(
                function () {
                    $(this).find('.label').stop().animate({bottom: 0}, 200, 'easeOutQuad');
                    $(this).find('img').stop().animate({top: -30}, 500, 'easeOutQuad');
                },
                function () {
                    $(this).find('.label').stop().animate({bottom: -40}, 200, 'easeInQuad');
                    $(this).find('img').stop().animate({top: 0}, 300, 'easeOutQuad');
                }
            );

        }

    };
    filterList.init();

});

$(document).ready(function() {

        $("#owl-demo").owlCarousel({
            items : 4,
            lazyLoad : true,
            autoPlay : true,
            navigation : true,
            navigationText : ["",""],
            rewindNav : false,
            scrollPerPage : false,
            pagination : false,
            paginationNumbers: false,
        });
    var defaults = {
        containerID: 'toTop', // fading element id
        containerHoverID: 'toTopHover', // fading element hover id
        scrollSpeed: 1200,
        easingType: 'linear'
    };


    $().UItoTop({ easingType: 'easeOutQuart' });

    $(".scroll").click(function(event){
        event.preventDefault();
        $('html,body').animate({scrollTop:$(this.hash).offset().top},1200);
    });

    });



