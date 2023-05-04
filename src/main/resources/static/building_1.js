let json_building_1 ;
var xhr = new XMLHttpRequest();
xhr.open('GET', '/buildings/geojson');
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.onload = function() {
    if (xhr.status === 200) {
         json_building_1 = JSON.parse(xhr.responseText);
    } else {
        console.error('Error loading data');
    }
};
xhr.send();