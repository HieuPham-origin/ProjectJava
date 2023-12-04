$(document).ready(function() {
    if (!$('#check-return').attr("checked")) {
        let current = $('input[name="dates"]').attr("value");
        console.log(current)
        $('input[name="dates"]').daterangepicker({ drops: 'down', startDate: Date.parse(current), singleDatePicker: true, locale: {format: "DD/MM/YY"}});
        $('input[name="dates"]').val(current);
    }
    else
        $('input[name="dates"]').daterangepicker({ drops: 'down', locale: {format: "DD/MM/YY"}});
    $('.flight-item.selected').click();
})

$('.flight-item').click(function() {
    $('.flight-item').each((i, e) => $(e).removeClass('selected'));
    $(this).addClass('selected')
})

$('#check-return').unbind('click')
$('#check-return').click(function() {
    if (this.checked)
        $('input[name="dates"]').daterangepicker({ drops: 'down', locale: {format: "DD/MM/YY"}});
    else
        $('input[name="dates"]').daterangepicker({ drops: 'down', singleDatePicker: true, locale: {format: "DD/MM/YY"}});
})

function getSubTime(start, end){
    s = start.split(':');
    e = end.split(':');
    min = e[1]-s[1];
    hour_carry = 0;
    if(min < 0){
        min += 60;
        hour_carry += 1;
    }
    hour = e[0]-s[0]-hour_carry;
    diff = hour + "h " + min + "m";
    return diff;
}

$('.flight-item').click(function(){
    $('#flight-all-container').html("")
    var weekday = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
    var dateText = new Date($(this).find('.dateDepartureText').text());
    $.ajax({
        url: "/search/searchTicket",
        type: "post",
        data:{
            "dateDeparture" : $(this).find('.dateDepartureText').text(),
            "departureText" : $(this).find('.departureText').val(),
            "destinationText" : $(this).find('.destinationText').val(),
            "sortByDuration" : $('input:radio[name=durationSort]:checked').val(),
        },
        success : function(data) {
            console.log(data)
            if (data.length===0){
                let html=`<p> No result for this flight </p>`
                $('#flight-all-container').append(html)
            } else{
                data.forEach(e=>{
                    let timeDeparture = e.departureTime.split(":");
                    let timeArrival =  e.arrivalTime.split(":");
                    var fightPriceCurrency = e.flight.flightPrice.toLocaleString('vi-VN');
                    var airlineLogoSrc = (e.flight.flightAirline === "VietNam Airlines") ?
                                "images/vna-logo.png" : "images/vietjet-logo.png";
                    let html = `
                        <div class="flight-container mb-3">
                            <div class="flight-info-container mb-3">
                                <div class="airline-logo">
                                    <img src="${airlineLogoSrc}" alt="" />
                                    <div class="airline-name">
                                        ${e.flight.flightAirline}
                                    </div>
                                </div>
                                <div class="vertical-seperator"></div>
                                <div class="flight-info">
                                    <div class="departure-info">
                                        <div class="day">${weekday[new Date(e.departureDay).getDay()]}</div>
                                        <div class="time">${timeDeparture[0]+":" +timeDeparture[1]}</div>
                                        <div class="airport-code">${e.flight.departureAirport.airportCode}</div>
                                    </div>
                                    <div class="duration-info mx-lg-4 mx-md-3 mx-sm-1">
                                        <div class="duration">${getSubTime(timeDeparture[0]+":" +timeDeparture[1],
                                        timeArrival[0]+":" +timeArrival[1])}</div>
                                        <div class="d-flex align-items-center">
                                            <i
                                                class="text-primary fa-regular fa-circle"
                                            ></i>
                                            <div
                                                class="horizontal-dashed-seperator"
                                            ></div>
                                            <i
                                                class="text-primary fa-solid fa-circle"
                                            ></i>
                                        </div>
                                        <a
                                            class="link-underline link-underline-opacity-0 link-primary btn-detail"
                                            >Details
                                            <i
                                                class="fa-solid fa-chevron-down"
                                            ></i
                                        ></a>
                                    </div>
                                    <div class="departure-info">
                                        <div class="day">${weekday[new Date(e.arrivalDay).getDay()]}</div>
                                        <div class="time">${timeArrival[0]+":" +timeArrival[1]}</div>
                                        <div class="airport-code">${e.flight.arrivalAirport.airportCode}</div>
                                    </div>
                                </div>
                                <div class="vertical-dashed-seperator"></div>
                                <div class="book-info d-flex flex-column align-items-center justify-content-center">
                                    <div class="price-info">
                                        <span class="price">${fightPriceCurrency} VND</span>
                                        <span class="text-secondary fs-4">
                                            / pax</span
                                        >
                                    </div>
                                    <input type="hidden" value="${e.id}" >
                                    <button class="btn-bookFlight btn-primary w-100">
                                        Book
                                    </button>
                                </div>
                            </div>
                            <div class="flight-detail-container collapse">
                                <div class="flight-detail">
                                    <div
                                        class="d-flex flex-column justify-content-between align-items-end"
                                    >
                                        <div>
                                            <div class="time">${timeDeparture[0]+":" +timeDeparture[1]}</div>
                                            <div class="date">${e.departureDay}</div>
                                        </div>
                                        <div class="duration">
                                        ${getSubTime(timeDeparture[0]+":" +timeDeparture[1],
                                                                        timeArrival[0]+":" +timeArrival[1])}
                                        </div>
                                        <div>
                                            <div class="time">${timeArrival[0]+":" +timeArrival[1]}</div>
                                            <div class="date">${e.arrivalDay}</div>
                                        </div>
                                    </div>
                                    <div class="vertical-dashed-seperator mx-4">
                                        <i
                                            class="text-primary fa-regular fa-circle"
                                            style="background-color: #f2f7ff"
                                        ></i>
                                        <i
                                            class="text-primary fa-solid fa-circle mt-auto"
                                        ></i>
                                    </div>
                                    <div
                                        class="d-flex flex-column justify-content-between"
                                    >
                                        <div>
                                            <div class="text-large fw-bold">
                                                ${e.flight.departureAirport.city} (${e.flight.departureAirport.airportCode})
                                            </div>
                                            <div
                                                class="text-small text-secondary"
                                            >
                                                ${e.flight.departureAirport.airportName}
                                            </div>
                                        </div>
                                        <div class="my-4">
                                            <div class="text-large fw-bold">
                                                ${e.flight.flightAirline}
                                            </div>
                                            <div class="text-large fw-bold">
                                                ${e.plane.planeName}
                                            </div>
                                            <div
                                                class="text-small text-secondary"
                                            >
                                                Baggage 23kg
                                            </div>
                                            <div
                                                class="text-small text-secondary"
                                            >
                                                Cabin baggage 1 x 7kg
                                            </div>
                                        </div>
                                        <div>
                                            <div class="text-large fw-bold">
                                                ${e.flight.arrivalAirport.city} (${e.flight.arrivalAirport.airportCode})
                                            </div>
                                            <div
                                                class="text-small text-secondary"
                                            >
                                                ${e.flight.arrivalAirport.airportName}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `
                    $('#flight-all-container').append(html)
                })
            }
        },
        error : function() {
            console.log("There was an error");
        }
    });
})

$(document).on('click', '.btn-detail', function() {
    if ($(this).hasClass('active'))
        $(this).removeClass('active').closest('.flight-container').find('.flight-detail-container').slideUp();
    else
        $(this).addClass('active').closest('.flight-container').find('.flight-detail-container').slideDown();
});

$(document).on('click','.btn-bookFlight', function(){
    var chosenDepartureFlight = $('.flight-item.selected').next('.flight-chosen');
    chosenDepartureFlight.html("");
    $.ajax({
        url: "/search/bookingFlight",
        type: "post",
        data:{
            "flightPlaneId" : $(this).closest(".book-info").find("input[type='hidden']").val()
        },
        success : function(data) {
            console.log(data)
            var fightPriceCurrency = data.flight.flightPrice.toLocaleString('vi-VN');
            let timeDeparture = data.departureTime.split(":");
            let timeArrival =  data.arrivalTime.split(":");
            var airlineLogoSrc = (data.flight.flightAirline === "VietNam Airlines") ?
                        "images/vna-logo.png" : "images/vietjet-logo.png";
            let html = `

                    <div class="d-flex mb-2">
                        <div class="logo me-2">
                            <img src="${airlineLogoSrc}" alt="">
                        </div>
                        <div>
                            <div class="fw-bold">${data.flight.flightAirline}</div>
                            <div class="text-secondary">${data.plane.planeName}</div>
                        </div>
                        <div class="ms-auto">
                            <span class="text-primary fw-bold"
                                > ${fightPriceCurrency} ₫</span
                            >
                            <span class="text-secondary">
                                / pax</span
                            >
                        </div>
                    </div>
                    <div class="d-flex justify-content-between">
                        <div>
                            <div class="fw-bold">${data.flight.departureAirport.airportCode}</div>
                            <div class="text-secondary">${timeDeparture[0]+":" +timeDeparture[1]}</div>
                        </div>
                        <div class="text-secondary">•</div>
                        <div class="d-flex flex-column align-items-center">
                            <i class="fa-solid fa-plane text-secondary"></i>
                            <div class="text-secondary">${getSubTime(timeDeparture[0]+":" +timeDeparture[1],
                            timeArrival[0]+":" +timeArrival[1])}</div>
                        </div>
                        <div class="text-secondary">•</div>

                        <div>
                            <div class="fw-bold">${data.flight.arrivalAirport.airportCode}</div>
                            <div class="text-secondary">${timeArrival[0]+":" +timeArrival[1]}</div>
                        </div>
                    </div>

            `
            chosenDepartureFlight.append(html)
            console.log(chosenDepartureFlight);

        },
        error : function() {
            console.log("There was an error");
        }
    });
});
$(document).on('click','.btn-bookFlight', function(){
    $('input[type="checkbox"][name="airline[]"]');
});
$(document).on('click','.input:radio[name=durationSort]', function(){
    $('.flight-item.selected').click();
});
