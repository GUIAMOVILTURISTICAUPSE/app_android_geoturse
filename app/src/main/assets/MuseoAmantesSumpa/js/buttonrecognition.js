var World = {
     planetsInfo: null,
     rotating: false,
     loaded: false,
     tracker: null,
     cloudRecognitionService: null,

     init: function initFn() {
        this.createTracker();
         this.createOverlays();
     },

     createTracker: function createTrackerFn() {
         this.cloudRecognitionService = new AR.CloudRecognitionService(
         "59d655b9ae78f0db354000ed3594545d",
         "5d6ef0e3f19455739de6afd5",
              {
                 onInitialized: World.showInfoBar,
                 onError: World.onError
             }
         );
            //alert("reconocimiento de repositorio");
         World.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
             onError: World.onError
         });
		// alert("paso del reconocimiento de repositorio");
     },

   createOverlays: function createOverlaysFn() {
		//alert("funcion overlays");
         this.bannerImg = new AR.ImageResource("assets/rotateButton.jpg", {
             onError: World.onError
         });
         this.bannerImgOverlay = new AR.ImageDrawable(this.bannerImg, 0.4, {
             translate: {  y: 0.6, x: 1 },
              onClick: World.toggleAnimateModel
         });
     },


     onRecognition: function onRecognitionFn(recognized, response) {
         if (recognized) {
             /* Clean Resources from previous recognitions. */
             if (World.wineLabel !== undefined) {
                 World.wineLabel.destroy();
             }

             if (World.wineName !== undefined) {
                 World.wineName.destroy();
             }
          //  alert("nombre del target... " + response.targetInfo.name + "--");


            World.fondoImg = new AR.ImageResource("assets/fondo2.png", {
              onError: World.onError
              });
                 World.orderfondoImg = new AR.ImageDrawable(World.fondoImg, 0.1, {
                     translate: { y: 1.3 },
                     scale: {  x: 8, y: 3 },
                    zOrder:2
               });

            World.wineName = new AR.Label(response.metadata.name, 0.05, {
                translate: {  y: 1.3 },
				scale: {  x: 3, y: 3 },
                zOrder: 3
            });

           World.objeto3d = new AR.Model("assets/"+ response.targetInfo.name +".wt3", {
             onLoaded: World.showInfoBar,
             onError: World.onError,
             scale: {  x: 2.5, y: 2.5, z: 2.5  },
             translate:{  x:0.2, y: 0  },
                zOrder: 1
             });

             World.appearingAnimation = World.createAppearingAnimation(World.objeto3d, 0.045);
             World.rotationAnimation = new AR.PropertyAnimation(World.objeto3d, "rotate.z", -25, 335, 10000);

            World.fondoImg1 = new AR.ImageResource("assets/fondo.png", {
             onError: World.onError
             });
                World.orderfondoImg1 = new AR.ImageDrawable(World.fondoImg1, 0.1, {
                    translate: {  x: -1.3  },
                    scale: {  x: 5, y: 3 },
                   zOrder:4
                });

            World.wineNameSci = new AR.Label(response.metadata.nombrecientifico, 0.05, {
                translate: {   x: -1.3 },
                  scale: {  x: 3, y: 3  },
                zOrder: 5
            });

            World.fondoImg2 = new AR.ImageResource("assets/fondob.png", {
                onError: World.onError
                });
           World.orderfondoImg2 = new AR.ImageDrawable(World.fondoImg2, 0.2, {
               translate: { y: -1.2, x:1.2  },
               scale: {  x: 4, y: 3 },
              zOrder:6
           });

            World.winedato = new AR.Label(response.metadata.dato1, 0.05, {
                translate: {  y: -1.2, x: 1.2  },
                  scale: {  x: 3,  y: 3  },
                zOrder: 7
            });

         World.winedato1 = new AR.Label(response.metadata.dato2, 0.05, {
            translate: {  y: -1.3, x: 1.2  },
            scale: {   x: 3, y: 3 },
            zOrder: 7
        });


             if (World.wineLabelAugmentation !== undefined) {
                 World.wineLabelAugmentation.destroy();
             }

              World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                 drawables: {
                     cam: [World.bannerImgOverlay, World.wineName,World.objeto3d,World.wineNameSci,World.winedato,World.winedato1,World.orderfondoImg,World.orderfondoImg1,World.orderfondoImg2]
                 },
                 onError: World.error
             });

             World.hideInfoBar();
         } else {
             /* Image recognition failed. An error message will be displayed to the user. */
             document.getElementById('errorMessage').innerHTML =
                 "<div class='errorMessage'>OH NO!. Fallo el reconocimineto del marcador, intenta nuevamente!</div>";

             setTimeout(function() {
                 var e = document.getElementById('errorMessage');
                 e.removeChild(e.firstChild);
             }, 3000);
         }
     },

     onRecognitionError: function onRecognitionError(errorCode, errorMessage) {
        // alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
     },

     scan: function scanFn() {
         this.cloudRecognitionService.recognize(this.onRecognition, this.onRecognitionError);
     },


    createAppearingAnimation: function createAppearingAnimationFn(model, scale) {
         var sx = new AR.PropertyAnimation(model, "scale.x", 0, scale, 1500, {
             type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
         });
         var sy = new AR.PropertyAnimation(model, "scale.y", 0, scale, 1500, {
             type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
         });
         var sz = new AR.PropertyAnimation(model, "scale.z", 0, scale, 1500, {
             type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
         });

         return new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [sx, sy, sz]);
     },

     appear: function appearFn() {
         World.hideInfoBar();
         /* Resets the properties to the initial values. */
         World.resetModel();
         World.appearingAnimation.start();
     },

     resetModel: function resetModelFn() {
         World.rotationAnimation.stop();
         World.rotating = false;
         World.objeto3d.rotate.z = -25;
     },

     toggleAnimateModel: function toggleAnimateModelFn() {
         if (!World.rotationAnimation.isRunning()) {
             if (!World.rotating) {
                 /* Starting an animation with .start(-1) will loop it indefinitely. */
                 World.rotationAnimation.start(-1);
                 World.rotating = true;
             } else {
                 /* Resumes the rotation animation */
                 World.rotationAnimation.resume();
             }
         } else {
             /* Pauses the rotation animation */
             World.rotationAnimation.pause();
         }

         return false;
     },

 /* Takes a screenshot. */
    captureScreen: function captureScreenFn() {
    //alert("entro en la funcion de capture");

            AR.platform.sendJSONObject({
                action: "capture_screen"
            });
      //      alert("pasa de la funcion ");

    },

     onError: function onErrorFn(error) {
        // alert(error);
     },

     hideInfoBar: function hideInfoBarFn() {
         document.getElementById("infoBox").style.display = "none";
     },

     showInfoBar: function worldLoadedFn() {
         document.getElementById("infoBox").style.display = "table";
         document.getElementById("loadingMessage").style.display = "none";
     }
 };

 World.init();