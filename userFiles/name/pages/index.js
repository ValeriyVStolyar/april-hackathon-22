const bar1 = $('.bar__one');
const bar2 =$('.bar__two');
const bar3 =$('.bar__three');
const avatar=$('.skills__avatar');

$(window).scroll(function(){
    if ( $(this).scrollTop() > 600 ) {
        bar1.addClass('bar__one_animate');
        bar2.addClass('bar__two_animate');
        bar3.addClass('bar__three_animate');
        avatar.addClass('skills__avatar_animate')
    }
});