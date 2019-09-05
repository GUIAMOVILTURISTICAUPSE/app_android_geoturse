  var World = {

             init: function initFn() {
                 this.createModelAtLocation();
             },

             createModelAtLocation: function createModelAtLocationFn() {

                 var location = new AR.RelativeLocation(null, 3, 1, 1);

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

                 this.geoObject = new AR.GeoObject(location, {
                     drawables: {
                         cam: [indicatorDrawable1],
                         indicator: [indicatorDrawable]
                     }
                 });
             },


            captureScreen: function captureScreenFn() {
              AR.platform.sendJSONObject({
                action: "capture_screen"
                });
              },

             onError: function onErrorFn(error) {
                //    alert(error);
                },

            worldLoaded: function worldLoadedFn() {
                document.getElementById("loadingMessage").style.display = "none";
            }
     };

 World.init();
