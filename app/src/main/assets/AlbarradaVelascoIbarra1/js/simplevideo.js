var World = {
 planetsInfo: null,
    rotating: false,
    hasVideoStarted: false,
    cloudRecognitionService: null,
    tracker: null,

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

        this.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
            onError: World.onError
        });
    },

    scan: function scanFn() {
    document.getElementById("rotarBtn").style.display = "none";
    document.getElementById("informacionBtn").style.display = "none";
        this.cloudRecognitionService.recognize(this.onRecognition, this.onRecognitionError);
      /*
         document.getElementById("rotarBtn").style.display = "block"; */
    },
    createOverlays: function createOverlaysFn() {
        /*
              this.targetCollectionResource = new AR.TargetCollectionResource("assets/qrsmarcador.wtc", {
            onError: World.onError
        });
         */

        /*

        this.tracker = new AR.ImageTracker(this.targetCollectionResource, {
            onTargetsLoaded: World.showInfoBar,
            onError: World.onError
        });
*/
        alert("va al video")
        this.video = new AR.VideoDrawable("assets/video.mp4", 0.40, {
                    translate: {
                        y: -0.3
                    },
                    onError: World.onError
          });
        alert("pageone")
       this.trackable = new AR.ImageTrackable(this.tracker, "pageOne", {
                  drawables: {
                      cam: [World.video]
                  },
                  onImageRecognized: function onImageRecognizedFn() {
                      if (this.hasVideoStarted) {
                          World.video.resume();
                      } else {
                          this.hasVideoStarted = true;
                          World.video.play(-1);
                      }
                      World.hideInfoBar();
                  },
                  onImageLost: function onImageLostFn() {
                      World.video.pause();
                  },
                  onError: World.onError
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