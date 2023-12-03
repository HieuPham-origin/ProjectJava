$('.search-place .dropdown-item').click(function() {
    let value = $(this).data("value");
    console.log(value)
    $(this).closest('.search-item').find('input').val(value);
})

$('input[name="dates"]').daterangepicker({ drops: 'up', locale: {format: "DD/MM/YY"}});

$('#check-return').click(function() {
    if (this.checked)
        $('input[name="dates"]').daterangepicker({ drops: 'up', locale: {format: "DD/MM/YY"}});
    else
        $('input[name="dates"]').daterangepicker({ drops: 'up', singleDatePicker: true, locale: {format: "DD/MM/YY"}});
})

$(".btn-minus").click(function() {
    let quantityElement = $(this).closest(".quantity-picker").find('.quantity');
    quantity = parseInt(quantityElement.text());
    if (quantity == 0)
        return;
    quantityElement.text(quantity - 1);
    updatePassenger();
})

$(".btn-plus").click(function() {

    let quantityElement = $(this).closest(".quantity-picker").find('.quantity');
    quantity = parseInt(quantityElement.text());
    quantityElement.text(quantity + 1);
    updatePassenger();
})

$('#btn-switch').click(function() {
    let tmp = $('#input-airport-departure').val();
    $('#input-airport-departure').val($('#input-airport-destination').val());
    $('#input-airport-destination').val(tmp);
})


function updatePassenger() {
    let adult = parseInt($('#quantity-adult').text());
    let child = parseInt($('#quantity-child').text());
    let infant = parseInt($('#quantity-infant').text());

    let texts = []
    if (adult > 0) texts.push(adult == 1 ? adult + " adult" : adult + " adults")
    if (child > 0) texts.push(child == 1 ? child + " child" : child + " children")
    if (infant > 0) texts.push(infant == 1 ? infant + " infant" : infant + " infants")
    $('#input-passenger').val("OK")
    $('#input-passenger').val(texts.join(", "))
}