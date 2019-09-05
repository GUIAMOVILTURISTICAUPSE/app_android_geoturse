  var World = {

             init: function initFn() {
                 this.createModelAtLocation();
             },

             createModelAtLocation: function createModelAtLocationFn() {

                 var location = new AR.RelativeLocation(null, 2, 1, 1);

                var imagenProyectada = new AR.ImageResource("assets/remacopse.jpg", {
                         onError: World.onError,
                        onLoaded: this.worldLoaded
                     });

              var indicatorDrawable1 = new AR.ImageDrawable(imagenProyectada, 5, {
                         verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                     });

                World.objeto3d = new AR.Model("assets/aves.wt3", {
                 onLoaded: World.showInfoBar,
                 onError: World.onError,
                 scale: {
                     x: 3,
                     y: 3,
                     z: 3
                 },
                 translate:{
                        x: 2,
                        y: 0
                 },
                   zOrder: 2
                 });


                 var indicatorImage = new AR.ImageResource("assets/indi.png", {
                     onError: World.onError
                 });

                 var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                     verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                 });

                 this.geoObject = new AR.GeoObject(location, {
                     drawables: {
                         cam: [indicatorDrawable1, World.objeto3d],
                         indicator: [indicatorDrawable]
                     }
                 });
             },



createWwwButton: function createWwwButtonFn(url, size, options) {
        options.onClick = function() {
        AR.context.openInBrowser(url);
    };
    return new AR.ImageDrawable(this.imgButton, size, options);
},

            captureScreen: function captureScreenFn() {
              AR.platform.sendJSONObject({
                action: "capture_screen"
                });
              },

             onError: function onErrorFn(error) {
                 //   alert(error);
                },

            worldLoaded: function worldLoadedFn() {
                document.getElementById("loadingMessage").style.display = "none";
            }
     };

 World.init();
