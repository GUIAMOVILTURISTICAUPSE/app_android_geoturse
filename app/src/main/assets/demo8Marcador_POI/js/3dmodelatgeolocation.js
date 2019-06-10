  var World = {

    init: function initFn() {
        this.createModelAtLocation();
    },

    createModelAtLocation: function createModelAtLocationFn() {

        /*
            First a location where the model should be displayed will be defined. This location will be relativ to
            the user.
        */
        var location = new AR.RelativeLocation(null, 5, 0, 2);

        /* Next the model object is loaded.
        var modelEarth = new AR.Model("assets/sable.wt3", {
            onLoaded: this.worldLoaded,
            onError: World.onError,
            scale: {
                x: 1,
                y: 1,
                z: 1
            }
        });*/
         var imagenProyectada = new AR.ImageResource("assets/amantes.jpg", {
                    onError: World.onError
                });

         var indicatorDrawable1 = new AR.ImageDrawable(imagenProyectada, 3, {
                    verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                });


        var indicatorImage = new AR.ImageResource("assets/indi.png", {
            onError: World.onError
        });

        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });

        /* INFORMACION DEL OBJETO EN LA TABLA DE INFORMACION*/
         document.getElementById("planetInfo").setAttribute("class", "planetInfo");
        document.getElementById("name").innerHTML = "AMANTES DE SUMPA ";
        document.getElementById("mass").innerHTML = "Proveniente de la cultura de Las Vegas de entre 5.000 y 6.000 años a. C ";
        document.getElementById("diameter").innerHTML = "Se trata de un entierro doble de un hombre y una mujer de aproximadamente 20 y 25 años de edad, que fueron sepultados cuidadosamente juntos. El hombre con su mano derecha sobre la cintura de la mujer y con la pierna derecha sobre la pelvis de ella.";
        document.getElementById("planetInfo").setAttribute("class", "infoVisible");


        /* Putting it all together the location and 3D model is added to an AR.GeoObject. */
        this.geoObject = new AR.GeoObject(location, {
            drawables: {
                cam: [indicatorDrawable1],
                indicator: [indicatorDrawable]
            }
        });
    }




    /*  worldLoaded: function worldLoadedFn() {
        document.getElementById("loadingMessage").style.display = "none";
    }*/
};

World.init();
