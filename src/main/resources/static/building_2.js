fetch('/buildings/geojson')
    .then(response => response.json())
    .then(data => {
        const json_building_2 = data;
        console.log(json_building_2)
        // здесь вы можете использовать полученный JSON в переменной json_building_1
    })
    .catch(error => console.error(error));
