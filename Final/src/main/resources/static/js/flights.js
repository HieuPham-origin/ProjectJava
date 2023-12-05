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

function getSubTime(start, end, a , b){
    s = start.split(':');
    e = end.split(':');
    min = e[1]-s[1];
    hour_carry = 0;
    if(min < 0){
        min += 60;
        hour_carry += 1;
    }
    if (a===b){
        hour = e[0]-s[0]-hour_carry;
        diff = hour + "h " + min + "m";
        return diff;
    } else {
        hour = e[0]-s[0]-hour_carry + 24;
        diff = hour + "h " + min + "m";
        return diff;
    }
}

$('.flight-item').click(function(){
    $('#flight-all-container').html("")
    var weekday = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
    //var dateText = new Date($(this).find('.dateDepartureText').text());
    $.ajax({
        url: "/search/searchTicket",
        type: "post",
        data:{
            "dateDeparture" : $(this).find('.dateDepartureText').text(),
            "departureText" : $(this).find('.departureText').val(),
            "destinationText" : $(this).find('.destinationText').val(),
            "sortByDuration" : $('input:radio[name=durationSort]:checked').val(),
            "sortByPrice": $('input:radio[name=priceSort]:checked').val(),
            "inputFilterAirline":$('#input-filter-airline').val(),
            "inputFilterDeparture":$('#input-filter-departure').val(),
            "inputFilterArrival":$('#input-filter-arrival').val()
        },
        success : function(data) {
            console.log(data)

            if (data.flightPlaneList.length===0){
                let html=`<p> No result for this flight </p>`
                $('#flight-all-container').append(html)
            } else{
                let insideHtml = ``
                data.ticketClassList.forEach(e=>{
                    insideHtml +=
                    `
                        <div class="ticket-class d-flex flex-column justify-content-between mx-4" id="ticket-class-${e.classId}">
                            <div class="card">
                              <div class="card-body">
                                <input class="input-rate-ticket-class" type="hidden" value="${e.rate}">
                                <h5 class="card-title">${e.className}</h5>
                                <p class="card-text">Baggage ${e.baggage} kg</p>
                                <p class="card-text">Cabin baggage 1 x ${e.cabinBaggage} kg</p>
                                <a class="button-ticket-class btn btn-primary">Book</a>
                              </div>
                            </div>
                        </div>
                    `
                })

                data.flightPlaneList.forEach(e=>{
                    let timeDeparture = e.departureTime.split(":");
                    let timeArrival =  e.arrivalTime.split(":");
                    var fightPriceCurrency = e.flight.flightPrice.toLocaleString('vi-VN');
                    var airlineLogoSrc = (e.flight.flightAirline === "Vietnam Airlines") ?
                                "images/vna-logo.png" : "images/vietjet-logo.png";
                    let html = `
                        <div class="flight-container mb-3">
                            <input type="hidden" class="ticket-price-save" value="${e.flight.flightPrice}">
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
                                        timeArrival[0]+":" +timeArrival[1],new Date(e.departureDay).getDay(), new Date(e.arrivalDay).getDay())}</div>
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
                                    <button class="btn-bookFlight btn btn-primary w-100">
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
                                    <div class="d-flex flex-column justify-content-between">
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
                                    ${insideHtml}
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
    $('#btn-continue-booking').prop('disabled', false);
    var chosenDepartureFlight = $('.flight-item.selected').next('.flight-chosen');
    var move = ""
    if (chosenDepartureFlight.attr("id")==="chosen-departure-flight"){
        move = "departure"
    } else{
        move = "arrival"
    }
    var idPrice = "input-each-price-" + move;
    chosenDepartureFlight.html("");
    $.ajax({
        url: "/search/bookingFlight",
        type: "post",
        data:{
            "flightPlaneId" : $(this).closest(".book-info").find("input[type='hidden']").val()
        },
        success : function(data) {
            console.log(data)
            let timeDeparture = data.departureTime.split(":");
            let timeArrival =  data.arrivalTime.split(":");
            var airlineLogoSrc = (data.flight.flightAirline === "Vietnam Airlines") ?
                        "images/vna-logo.png" : "images/vietjet-logo.png";
            var rateInt = parseInt($('#rate-save').val())
            var fightPriceInt = data.flight.flightPrice*rateInt
            var fightPriceCurrency = (data.flight.flightPrice*rateInt).toLocaleString('vi-VN');

            $('#input-sub-total').val($('#input-sub-total').val() + "/" + data.flight.departureAirport.airportCode + ":" + data.flight.flightPrice*rateInt)
            var inputString = $('#input-sub-total').val()

            var inputString =  inputString.split('/');
            var departureMoney = 0;
            var arrivalMoney = 0;
            $.each(inputString,function(index,value){
                if (value!=="0"){
                    var valueSplit = value.split(':')
                    if (valueSplit[0]===data.flight.departureAirport.airportCode){
                        departureMoney = parseInt(valueSplit[1]);
                    } else if (valueSplit[0]===data.flight.arrivalAirport.airportCode){
                        arrivalMoney = parseInt(valueSplit[1]);
                    }
                }
            })

            $('#text-sub-total').text((departureMoney + arrivalMoney).toLocaleString('vi-VN') + " VND")
            var inputSumPaxValue = parseInt($('#input-sum-pax').text())
            $('#text-total').text(((departureMoney + arrivalMoney)*inputSumPaxValue).toLocaleString('vi-VN') + " VND")
            let html = `
                    <input id="${idPrice}-flightId" type="hidden" value="${data.id}">
                    <div class="d-flex mb-2">
                        <div class="logo me-2">
                            <img src="${airlineLogoSrc}" alt="">
                        </div>
                        <div>
                            <div class="fw-bold">${data.flight.flightAirline}</div>
                            <div class="text-secondary">${data.plane.planeName} • </div>
                            <div class="text-secondary">${data.plane.planeName}</div>
                        </div>
                        <div class="ms-auto">
                            <input type="hidden" id="${idPrice}-normal" value="${data.flight.flightPrice}">
                            <input type="hidden" id="${idPrice}" value="${fightPriceInt}" >
                            <span class="input-each-price text-primary fw-bold"
                                > ${fightPriceCurrency} VND</span
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
$('input:radio[name=durationSort]').click(function(){
    $('.flight-item.selected').click();
})
$('input:radio[name=priceSort]').click(function(){
    $('.flight-item.selected').click();
})

function getSubstringAfter(mainString, searchString) {
    var index = mainString.indexOf(searchString);
    if (index !== -1) {
        if (index==0){
            return mainString.substring(searchString.length);
        } else{
            return mainString.substring(0,index);
        }
    } else {
        return null;
    }
}

$('input[type="checkbox"][name="airline[]"]').change(function(){
    var inputFilterAirline = $('#input-filter-airline').val();
    if (this.checked){
        $('#input-filter-airline').val(inputFilterAirline + this.value);
    } else{
         $('#input-filter-airline').val(getSubstringAfter(inputFilterAirline,this.value));
    }
    $('.flight-item.selected').click();
})

$('input[type="checkbox"][name="departure[]"]').change(function(){
    var inputFilterDeparture = $('#input-filter-departure').val();
    if (this.checked){
        $('#input-filter-departure').val(inputFilterDeparture + this.value);
    } else{
         $('#input-filter-departure').val(getSubstringAfter(inputFilterDeparture,this.value));
    }
    $('.flight-item.selected').click();
})

$('input[type="checkbox"][name="arrival[]"]').change(function(){
    var inputFilterArrival= $('#input-filter-arrival').val();
    if (this.checked){
        $('#input-filter-arrival').val(inputFilterArrival + this.value);
    } else{
        $('#input-filter-arrival').val(getSubstringAfter(inputFilterArrival,this.value));
    }
    $('.flight-item.selected').click();
})

$(document).on("click",".button-ticket-class",function() {
    $('#rate-save').val("")
    var rateStr = $(this).closest(".ticket-class").find(".input-rate-ticket-class").val()
    var economyValue = $(this).closest(".flight-container").find(".ticket-price-save").val()
    var rateInt = parseInt(rateStr)
    var economyValueInt = parseInt(economyValue)
    $('#rate-save').val(rateStr)
    var fileContainer = $(this).closest(".flight-detail-container").prev(".flight-info-container").find(".btn-bookFlight").click();

    $(this).closest(".flight-container").find(".price").text((economyValueInt * rateInt).toLocaleString('vi-VN') + "" + " VND")
});

function extractNumbersFromString(inputString) {
    // Use a regular expression to find all matches of digits in the input string
    var numbersArray = inputString.match(/\d+/g);

    // Convert the array of string numbers to an array of integers
    var numbers = numbersArray.map(function (str) {
        return parseInt(str, 10); // Use parseInt to convert each string to an integer
    });

    return numbers;
}

$('#btn-continue-booking').click(function(){
    $('#show-exception').text("")
    isChecked = $('input[name="checkReturn"]:checked').length > 0
    var departureHasChildDiv = $('#chosen-departure-flight').find('div').length > 0;
    var arrivalHasChildDiv =  $('#chosen-arrival-flight').find('div').length > 0;
    if (isChecked){
        if (departureHasChildDiv && arrivalHasChildDiv){
            var inputDepartureFlightId = $("#input-each-price-departure-flightId").val();
            var inputArrivalFlightId = $("#input-each-price-arrival-flightId").val();
            var inputDepartureFlightIdTotalMoney = parseInt($("#input-each-price-departure").val());
            var inputArrivalFlightIdTotalMoney =  parseInt($("#input-each-price-arrival").val());
            var inputDepartureFlightIdNormalMoney = parseInt($("#input-each-price-departure-normal").val())
            var inputArrivalFlightIdNormalMoney = parseInt($("#input-each-price-arrival-normal").val())
            var arrivalTicketClassId = inputArrivalFlightIdTotalMoney / inputArrivalFlightIdNormalMoney;
            var departureTicketClassId = inputDepartureFlightIdTotalMoney / inputDepartureFlightIdNormalMoney

            var stringPassenger =  $("#input-passenger").val()

            var indexAdult = stringPassenger.indexOf("adult");
            var numberCountAdult = "0"
            if (indexAdult!=-1){
                numberCountAdult = stringPassenger.substring(indexAdult-2,indexAdult-1)
            }

            var indexChildren = stringPassenger.indexOf("child");
            var numberCountChild = "0"
            if (indexChildren!=-1){
                numberCountChild = stringPassenger.substring(indexChildren-2,indexChildren-1)
            }

            var indexInfant = stringPassenger.indexOf("infant");
            var numberCountInfant = "0"
            if (indexInfant!=-1){
                numberCountInfant = stringPassenger.substring(indexInfant-2,indexInfant-1)
            }

            var passengerNumInfo = numberCountAdult + "/" + numberCountChild + "/" + numberCountInfant

            $.ajax({
                url: "/sendDataSessionForReturn",
                type: "post",
                data:{
                    "departureFlightId" : inputDepartureFlightId,
                    "arrivalFlightId": inputArrivalFlightId,
                    "departureTicketClassId": departureTicketClassId,
                    "arrivalTicketClassId": arrivalTicketClassId,
                    "passengerNumInfo": passengerNumInfo
                },
                success : function(data) {

                    window.location.href = "/booking"
                },
                error : function() {
                    console.log("There was an error");
                }
            });
        } else{
            $('#show-exception').text("Incomplete information flight ticket")
            $(this).prop('disabled', true);
        }
    } else{
        if (departureHasChildDiv){
            var inputDepartureFlightId = $("#input-each-price-departure-flightId").val();
            var inputDepartureFlightIdTotalMoney = parseInt($("#input-each-price-departure").val());
            var inputDepartureFlightIdNormalMoney = parseInt($("#input-each-price-departure-normal").val())
            var departureTicketClassId = inputDepartureFlightIdTotalMoney / inputDepartureFlightIdNormalMoney

             var stringPassenger =  $("#input-passenger").val()

            var indexAdult = stringPassenger.indexOf("adult");
            var numberCountAdult = "0"
            if (indexAdult!=-1){
                numberCountAdult = stringPassenger.substring(indexAdult-2,indexAdult-1)
            }

            var indexChildren = stringPassenger.indexOf("child");
            var numberCountChild = "0"
            if (indexChildren!=-1){
                numberCountChild = stringPassenger.substring(indexChildren-2,indexChildren-1)
            }

            var indexInfant = stringPassenger.indexOf("infant");
            var numberCountInfant = "0"
            if (indexInfant!=-1){
                numberCountInfant = stringPassenger.substring(indexInfant-2,indexInfant-1)
            }

            var passengerNumInfo = numberCountAdult + "/" + numberCountChild + "/" + numberCountInfant

            $.ajax({
                url: "/sendDataSessionForNotReturn",
                type: "post",
                data:{
                    "departureFlightId" : inputDepartureFlightId,
                    "departureTicketClassId": departureTicketClassId,
                    "passengerNumInfo": passengerNumInfo
                },
                success : function(data) {
                    console.log(data)
                    window.location.href = "/booking"
                },
                error : function() {
                    console.log("There was an error");
                }
            });
        } else{
            $('#show-exception').text("Incomplete information flight ticket")
            $(this).prop('disabled', true);
        }
    }
})
