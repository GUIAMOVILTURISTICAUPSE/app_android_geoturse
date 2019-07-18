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

                     this.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
                         onError: World.onError
                     });
                 },

                 createOverlays: function createOverlaysFn() {

                     alert("Aqui se crea el overlay basico");
                     console.log("Aqui se crea el overlay basico");

                     this.bannerImg = new AR.Model("assets/mammut.wt3", {
                     onLoaded: World.showInfoBar,
                     onError: World.onError,
                     scale: {
                         x: 0.1,
                         y: 0.1,
                         z: 0.1
                     }
                      });
                     this.appearingAnimation = this.createAppearingAnimation(this.bannerImg, 0.045);
                     this.rotationAnimation = new AR.PropertyAnimation(this.bannerImg, "rotate.z", -25, 335, 10000);


                     var myJSONBannerImg = JSON.stringify(this.bannerImg);
                     alert("myJSONBannerImg: " + myJSONBannerImg);
                     console.log("myJSONBannerImg: " + myJSONBannerImg);

                 },

                 onRecognition: function onRecognitionFn(recognized, response) {
                     if (recognized) {
                         var myJSON = JSON.stringify(response);
                         console.log("JSon en la consola:" + myJSON);

                         console.log("Reconocio 1");
                         alert("Reconocio 1");
                         /* Clean Resources from previous recognitions. */
                         if (World.wineLabel !== undefined) {
                              alert("Alerta esta indefinido wineLabel"+ World.wineLabel);
                             World.wineLabel.destroy();
                         }

                         if (World.wineLabelOverlay !== undefined) {
                              alert("Alerta esta indefinido wineLabelOverlay "+ World.wineLabelOverlay);
                             World.wineLabelOverlay.destroy();
                         }

                         console.log("Reconocio 2 :" + response.targetInfo.name);
                         alert(response.targetInfo.name);

                         World.wineLabel = new AR.ImageResource("assets/rotateButton.jpg");

                         console.log("Reconocio 3:" + World.wineLabel);
                         alert(World.wineLabel);

                         World.wineLabelOverlay = new AR.ImageDrawable(World.wineLabel, 0.3, {
                             translate: {
                                 x: 0.5,
                                 y: 0.6
                             }
                             /*,
                               onClick: World.toggleAnimateModel
             */
                         });

                          World.wineName = new AR.Label(response.metadata.name, 0.06, {
                                     translate: {
                                         y: 0.72
                                     },
                                     zOrder: 2
                                 });


                         document.getElementById("rotarBtn").style.display = "inline";
                         document.getElementById("informacionBtn").style.display = "inline";
                         if (World.wineLabelAugmentation !== undefined) {
                             console.log("Ok. WineLabelAugmentation Definido y se borrarse como el Crush");
                             alert("Ok. WineLabelAugmentation Definido y se borrarse como el Crush");
                             World.wineLabelAugmentation.destroy();
                         }

                     World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                             drawables: {
                                 cam: [World.bannerImg,World.wineLabelOverlay,World.wineName]
                             },
                             onImageRecognized: World.appear,
                              onError: World.onError
                         });


                         console.log("Esto no es un error, pero si fuese se deberia ver asi: " + World.error);
                         var myJSONWLA = JSON.stringify(World.wineLabelAugmentation);
                         console.log("Reconocio 4:" + myJSONWLA);
                         alert(myJSONWLA);




                        World.orderNowButtonOverlay.onClick = function() {
                            AR.context.openInBrowser(response.metadata.shop_url);
                        }

                        if (World.orderNowAugmentation !== undefined) {
                            World.orderNowAugmentation.destroy();
                        }

                        World.orderNowAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                            drawables: {
                                cam: World.orderNowButtonOverlay
                            }
                        });






                         World.hideInfoBar();
                     } else {
                         /* Image recognition failed. An error message will be displayed to the user. */
                         document.getElementById('errorMessage').innerHTML =
                             "<div class='errorMessage'>Recognition failed, please try again!</div>";

                         setTimeout(function() {
                             alert("Guayando");
                             console.log("Falla guayando 2");
                             var e = document.getElementById('errorMessage');
                             e.removeChild(e.firstChild);
                         }, 3000);
                     }
                 },

                 onRecognitionError: function onRecognitionError(errorCode, errorMessage) {
                     alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
                 },


                 scan: function scanFn() {
                 document.getElementById("rotarBtn").style.display = "none";
                 document.getElementById("informacionBtn").style.display = "none";
                     this.cloudRecognitionService.recognize(this.onRecognition, this.onRecognitionError);
                   /*
                      document.getElementById("rotarBtn").style.display = "block"; */
                 },

              createAppearingAnimation: function createAppearingAnimationFn(model, scale) {
                         alert("funcion girar");
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
                     World.modelCar.rotate.z = -25;
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

                  planetClicked: function() {
                             document.getElementById("planetInfo").setAttribute("class", "planetInfo");
                           document.getElementById("name").innerHTML = "Lobo marino o león marino ";
                           document.getElementById("mass").innerHTML = "Este animal costero es por lo general juguetón y curioso, pasa la mayor parte de su tiempo cerca del litoral. Es capaz de sumergirse a profundidades de 350 metros y puede permanecer debajo del agua hasta diez minutos";
                           document.getElementById("diameter").innerHTML = "Mide entre 1,5 y 2,5 metros ";
                           document.getElementById("planetInfo").setAttribute("class", "infoVisible");
                       },

                 planetDbClicked: function() {
                       document.getElementById("planetInfo").setAttribute("class", "planetInfo");
                       document.getElementById("name").innerHTML = " ";
                       document.getElementById("mass").innerHTML = " ";
                       document.getElementById("diameter").innerHTML = " ";
                       document.getElementById("planetInfo").setAttribute("class", "infoVisible");


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