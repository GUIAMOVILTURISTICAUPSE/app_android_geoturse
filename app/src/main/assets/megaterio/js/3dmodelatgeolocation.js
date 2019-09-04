  var World = {

             init: function initFn() {
                 this.createModelAtLocation();
             },

             createModelAtLocation: function createModelAtLocationFn() {

                 var location = new AR.RelativeLocation(null, 5, 0, 2);

                var imagenProyectada = new AR.ImageResource("assets/muralmegafauna.png", {
                         onError: World.onError,
                        onLoaded: this.worldLoaded
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


                 /* INFORMACION DEL OBJETO EN LA TABLA DE INFORMACION
                 document.getElementById("planetInfo").setAttribute("class", "planetInfo");
                 document.getElementById("name").innerHTML = "Tigre dientes de Sable ";
                 document.getElementById("mass").innerHTML = "El museo contiene una megafauna del Pleistoceno";
                 document.getElementById("diameter").innerHTML = "Contiene una replica de este felino.";
                 document.getElementById("planetInfo").setAttribute("class", "infoVisible");
*/

                 this.geoObject = new AR.GeoObject(location, {
                     drawables: {
                         cam: [indicatorDrawable1],
                         indicator: [indicatorDrawable]
                     }
                 });
             },

             onError: function onErrorFn(error) {
                    alert(error);
                },

            worldLoaded: function worldLoadedFn() {
                document.getElementById("loadingMessage").style.display = "none";
            }
     };

 World.init();
