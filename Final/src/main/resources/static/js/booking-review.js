const tooltipTriggerList = document.querySelectorAll(
    '[data-bs-toggle="tooltip"]'
);
const tooltipList = [...tooltipTriggerList].map(
    (tooltipTriggerEl) => new bootstrap.Tooltip(tooltipTriggerEl)
);

$(document).ready(() => {
    updateBaggagePrice();
    document.querySelector("#scroll-here").scrollIntoView({
    });
})

$("#modal-baggage .select-baggage-item").click(function () {
    $(".select-baggage-item").each((i, e) => {
        $(e).removeClass("selected");
    });
    $(this).addClass("selected");

    let flight = $(this).data("flight");
    $(".select-baggage-container").each((i, e) => {
        if ($(e).data("flight") != flight) $(e).addClass("d-none");
        else $(e).removeClass("d-none");
    });
});

$("#modal-seat .select-flight-item").click(function () {
    $(".select-flight-item").each((i, e) => {
        $(e).removeClass("selected");
    });
    $(this).addClass("selected");

    let flight = $(this).data("flight");
    $("#modal-seat .select-passenger-container").each((i, e) => {
        if ($(e).data("flight") != flight) $(e).addClass("d-none");
        else $(e).removeClass("d-none");
    });

    $("#modal-seat .select-seat-container").each((i, e) => {
        if ($(e).data("flight") != flight) $(e).addClass("d-none");
        else $(e).removeClass("d-none");
    });
});

$("#modal-seat .select-passenger-item").click(function () {
    let container = $(this).closest(".select-passenger-container");
    container.find(".select-passenger-item").each((i, e) => {
        $(e).removeClass("selected");
    });
    $(this).addClass("selected");
});

$('.select-baggage-container input[type="radio"]').change(function () {
    updateBaggagePrice();
});

$(".seat-box").click(function () {
    if ($(this).hasClass("unavailable"))
        return;

    let number = $(this).data("number");
    let container = $(this).closest(".select-seat-container")
    let flight = container.data("flight");
    let passengerElem = $(
        `#modal-seat .select-passenger-container[data-flight="${flight}"] .select-passenger-item.selected`
    );
    let passengerNumber = passengerElem.data("passenger") 
    if ($(this).hasClass("available")) {
        passengerElem.find(".seat").text(number);
        passengerElem.find(".input-chosen-seat-detail-id").val($(this).data("id"));
        container.find(`.seat-box.selected[data-selected-by="${passengerNumber}"]`).removeClass("selected").addClass("available").text("").removeAttr("data-selected-by")
        $(this).addClass("selected").removeClass("available").text(passengerNumber).attr("data-selected-by", passengerNumber);
        return;
    }
    if ($(this).hasClass("selected")) {

        if ($(this).data("selected-by") === passengerNumber) {
            $(this).removeClass("selected").addClass("available").text("").removeAttr("data-selected-by")
            passengerElem.find(".seat").text("No preference");
            passengerElem.find(".input-chosen-seat-detail-id").val(0);
            return;
        }
    }
});

function updateBaggagePrice() {
    let subtotal = 0;
    $(".select-baggage-container").each((i, e) => {
        let flight = $(e).data("flight");
        let weight = 0;
        let price = 0;
        $(e)
            .find('input[type="radio"]:checked')
            .each((i, b) => {
                weight += parseInt($(b).data("weight"));
                price += parseInt($(b).data("price"));
                console.log(b);
            });
        console.log(`.select-baggage-item[data-flight="${flight}"] .price`);

        $(`.select-baggage-item[data-flight="${flight}"] .weight`).text(
            weight + " kg"
        );
        $(`.select-baggage-item[data-flight="${flight}"] .price`).text(
            formatter().format(price)
        );
        subtotal += price;
    });
    $("#modal-baggage .subtotal").text(formatter().format(subtotal));
}

$("#btn-reset-seat").click(() => {
    let reset = confirm("Are you sure to reset your seat selection ?");
    if (reset) {
        $('#modal-seat .seat').text('No preference');
        $('#modal-seat .input-chosen-seat-detail-id').val(0);
        $('#modal-seat').modal('show');
    }
})

function formatter() {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",

        // These options are needed to round to whole numbers if that's what you want.
        //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
        //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
    });
}

