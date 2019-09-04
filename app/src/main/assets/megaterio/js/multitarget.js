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
             "6f19a9fd0be6fd7f4ba2033b59d4d2d2",
             "5cc889e4393b2d3ca160696c",
              {
                 onInitialized: World.showInfoBar,
                 onError: World.onError
             }
         );
            alert("reconocimiento de repositorio");
         World.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
             onError: World.onError
         });
     },

   createOverlays: function createOverlaysFn() {
 alert("funcion overlays");
         this.bannerImg = new AR.ImageResource("assets/rotateButton.jpg", {
             onError: World.onError
         });
         this.bannerImgOverlay = new AR.ImageDrawable(this.bannerImg, 0.4, {
             translate: {
                 y: 0.6,
                 x: 0.6
             }
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
            alert("nombre del target... " + response.targetInfo.name + "--");
             World.wineLabel = new AR.ImageResource("assets/" + response.targetInfo.name + ".jpg", {
                 onError: World.onError
             });
             World.wineLabelOverlay = new AR.ImageDrawable(World.wineLabel, 0.3, {
                 translate: {
                     x: -0.5,
                     y: -0.6
                 },
                 zOrder: 1
             });


             World.wineName = new AR.Label(response.metadata.name, 0.06, {
                translate: {
                    y: 0.72 },
                zOrder: 2
            });

           World.objeto3d = new AR.Model("assets/"+ response.targetInfo.name +".wt3", {
             onLoaded: World.showInfoBar,
             onError: World.onError,
             scale: {
                 x: 2,
                 y: 2,
                 z: 2
             },
             translate:{
             x:0,
             y: 1
             },
                zOrder: 3
             });


             if (World.wineLabelAugmentation !== undefined) {
                 World.wineLabelAugmentation.destroy();
             }

              World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                 drawables: {
                     cam: [World.bannerImgOverlay, World.wineLabelOverlay,World.wineName,World.objeto3d]
                 },
                 onError: World.error
             });

             World.hideInfoBar();
         } else {
             /* Image recognition failed. An error message will be displayed to the user. */
             document.getElementById('errorMessage').innerHTML =
                 "<div class='errorMessage'>Recognition failed, please try again!</div>";

             setTimeout(function() {
                 var e = document.getElementById('errorMessage');
                 e.removeChild(e.firstChild);
             }, 3000);
         }
     },

     onRecognitionError: function onRecognitionError(errorCode, errorMessage) {
         alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
     },

     scan: function scanFn() {
         this.cloudRecognitionService.recognize(this.onRecognition, this.onRecognitionError);
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