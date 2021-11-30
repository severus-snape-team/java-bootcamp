let map;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 46.77254971477885, lng: 23.621516628328187 },
        zoom: 13,
        clickableIcons: false,
    });

    const image = {
        url:  "/public/mapsPin.svg",
        size: new google.maps.Size(65, 65),
        scaledSize: new google.maps.Size(65, 65),
    }

    var marker;

    map.addListener("click", (mapsMouseEvent) => {

        placeMarker(mapsMouseEvent.latLng);

        latLng = mapsMouseEvent.latLng.toJSON();

        document.getElementById("lat").value = latLng.lat;
        document.getElementById("lng").value = latLng.lng;

    });

    const latitude = document.getElementById("lat");
    const longitude = document.getElementById("lng");

    placeMarker(new google.maps.LatLng(latitude.value, longitude.value));

    latitude.addEventListener('input', (locationEvent) =>{

        const location = new google.maps.LatLng(latitude.value, longitude.value);
        placeMarker(location);
    });

    longitude.addEventListener('input', (locationEvent) =>{

        const location = new google.maps.LatLng(latitude.value, longitude.value);
        placeMarker(location);
    });


    function placeMarker(location) {

        if (marker == null)
        {
            marker =new google.maps.Marker({
                position: location,
                icon: image,
                map: map,
                clickable: false,
            });
        }
        else
        {
            marker.setPosition(location);
        }
    }
}

