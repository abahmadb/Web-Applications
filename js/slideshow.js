
        var margine = 0;

        var cursore = 0;

        var riquadro = document.querySelector("#riquadro");

        var immagini = document.querySelectorAll("#riquadro div");

        for(let i = 1; i < immagini.length; i++)
            riquadro.removeChild(immagini.item(i));

        riquadro.prepend(immagini.item(immagini.length - 1));

        var on_going = false;

        function cambia_immagine(valore) {

            if(on_going) return;

            on_going = true;

            if(valore > 0){

                cursore++;

                if(cursore >= immagini.length) cursore = 0;

                riquadro.appendChild(immagini.item(cursore));

                riquadro.style.transition = "margin-left 1s";
                riquadro.style.marginLeft = '-2000px';

                setTimeout(function(){
                    riquadro.removeChild(riquadro.firstElementChild);
                    riquadro.style.transition = "";
                    riquadro.style.marginLeft = '-1000px';
                    on_going = false;
                }, 1000);
            }
            else{
                cursore--;

                if(cursore < 0) cursore = immagini.length - 1;

                riquadro.style.transition = "margin-left 1s";
                riquadro.style.marginLeft = "0";

                setTimeout(function(){

                    var app = cursore-1;

                    if(app < 0) app = immagini.length - 1;

                    riquadro.style.transition = "";
                    riquadro.style.marginLeft = '-1000px';

                    riquadro.prepend(immagini.item(app));
                    riquadro.removeChild(riquadro.lastElementChild);
                    on_going = false;
                }, 1000);

                
            }

        }//cambia_immagine
