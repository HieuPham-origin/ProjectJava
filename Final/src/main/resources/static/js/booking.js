
fetch("js/country-data.json").then(res => res.json())
    .then(data => {
        console.log(data);
        let html = "";
        data.forEach(country => {
            if (country.name === "Viet Nam")
                html += `<option value="${country.name}" selected>${country.name}</option>`   
            else
                html += `<option value="${country.name}">${country.name}</option>`                               
        })
        $('.select-nationality').append(html);
    })