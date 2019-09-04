var World = {
    loaded: false,
    tracker: null,
    cloudRecognitionService: null,

    init: function initFn() {
        this.createTracker();
        this.createOverlays();
    },

    createTracker: function createTrackerFn() {
   // alert("Reconocimiento de la coleccion");
        this.cloudRecognitionService = new AR.CloudRecognitionService(
          "59d655b9ae78f0db354000ed3594545d",
          "5d6ef0e3f19455739de6afd5",
          {
                onInitialized: World.trackerLoaded,
                onError: World.onError
            }
        );

        World.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
            onError: World.onError
        });
    },

    startContinuousRecognition: function startContinuousRecognitionFn(interval) {
     //  alert("funcion starcontinuos");
        this.cloudRecognitionService.startContinuousRecognition(interval, this.onInterruption, this.onRecognition, this.onRecognitionError);
    },

    createOverlays: function createOverlaysFn() {

        this.orderNowButtonImg = new AR.ImageResource("assets/botonpresiona.png", {
            onError: World.onError
        });
        this.orderNowButtonOverlay = new AR.ImageDrawable(this.orderNowButtonImg, 0.1, {
          translate: { y: 0,x: 1.3  },
          scale: {  x: 3, y: 3 },
         zOrder:1
        });
    },
      onRecognition: function onRecognitionFn(recognized, response) {

        if (recognized) {
            /* Clean Resources from previous recognitions. */
           if (World.wineLabel !== undefined) {
                World.wineLabel.destroy();
            }

            if (World.wineLabelOverlay !== undefined) {
                World.wineLabelOverlay.destroy();
            }


      // alert("nombre del objeto ..."+response.targetInfo.name )
        World.objeto3d = new AR.Model("assets/"+ response.targetInfo.name +".wt3", {
             onLoaded: World.showInfoBar,
             onError: World.onError,
             scale: {  x: 2.5,  y: 2.5,  z: 2.5 },
             translate:{  x:0,   y: 0.2 },
             rotate:{z:30},
               zOrder: 2
             });

             World.fondoImg = new AR.ImageResource("assets/fondo2.png", {
              onError: World.onError
              });
                 World.orderfondoImg = new AR.ImageDrawable(World.fondoImg, 0.1, {
                     translate: { y: 1.3  },
                     scale: {  x: 6, y: 4 },
                    zOrder:3
               });

            World.wineName = new AR.Label(response.metadata.name, 0.05, {
                translate: { y: 1.3 },
                scale: { x: 3, y: 3 },
                zOrder: 4
            });

            World.fondoImg1 = new AR.ImageResource("assets/fondo.png", {
             onError: World.onError
             });
            World.orderfondoImg1 = new AR.ImageDrawable(World.fondoImg1, 0.1, {
                translate: {  x: -1.5  },
                scale: {  x: 5, y: 3 },
               zOrder:5
            });

            //nombre cientifico
             World.wineNameSci = new AR.Label(response.metadata.nombrecientifico, 0.05, {
                translate: { x: -1.5 },
                  scale: { x: 3, y: 3 },
                zOrder: 6
            });

            World.fondoImg2 = new AR.ImageResource("assets/fondob.png", {
             onError: World.onError
             });
            World.orderfondoImg2 = new AR.ImageDrawable(World.fondoImg2, 0.2, {
                translate: { y: -1, x:1.2  },
                scale: {  x: 4, y: 3 },
               zOrder:7
            });

            World.winedato = new AR.Label(response.metadata.dato1, 0.05, {
                translate: { y: -1, x: 1.2  },
                  scale: {  x: 3,  y: 3 },
                zOrder: 8
            });

             World.winedato1 = new AR.Label(response.metadata.dato2, 0.05, {
                translate: {  y: -1.2, x: 1.2  },
                  scale: { x: 3, y: 3 },
                zOrder: 9
            });

            if (World.wineLabelAugmentation !== undefined) {
                World.wineLabelAugmentation.destroy();
            }

            World.orderNowButtonOverlay.onClick = function() {
                AR.context.openInBrowser(response.metadata.shop_url);
            };

            World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                drawables: {
                    cam: [ World.objeto3d, World.wineName, World.orderNowButtonOverlay,World.wineNameSci,World.winedato,World.winedato1,World.orderfondoImg,World.orderfondoImg1,World.orderfondoImg2]
                },
                onError: World.onError
            });
            World.hideInfoBar();
        }
    },

    onRecognitionError: function onRecognitionErrorFn(errorCode, errorMessage) {
        World.cloudRecognitionService.stopContinuousRecognition();
     //  alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
    },

    onInterruption: function onInterruptionFn(suggestedInterval) {
        World.cloudRecognitionService.stopContinuousRecognition();
        World.startContinuousRecognition(suggestedInterval);
    },

    trackerLoaded: function trackerLoadedFn() {
        World.startContinuousRecognition(750);
        World.showInfoBar();
    },

    captureScreen: function captureScreenFn() {
              AR.platform.sendJSONObject({
                action: "capture_screen"
            });
    },

    onError: function onErrorFn(error) {
        alert(error);
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