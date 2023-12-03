$('.btn-detail').click(function() {
    if ($(this).hasClass('active')) 
        $(this).removeClass('active').closest('.flight-container').find('.flight-detail-container').slideUp();
    else 
        $(this).addClass('active').closest('.flight-container').find('.flight-detail-container').slideDown();
})

$('.flight-item').click(function() {
    $('.flight-item').each((i, e) => $(e).removeClass('selected'));
    $(this).addClass('selected')
})

$('input[name="dates"]').daterangepicker({ drops: 'down', locale: {format: "DD/MM/YY"}});