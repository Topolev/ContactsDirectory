<!--Script for lang-->
var body = document.getElementsByTagName("body")[0];
var lang = document.getElementById("lan");
var choose_lang = document.getElementById("choose-lang");

body.onclick = function(event){
    event = event || window.event;
    if (!lang.contains(event.target)){
        lang.style.overflow = "hidden";
    }
}


lang.onclick = function(event){
    event = event || window.event;
    console.log(event.target)
    this.style.overflow = "visible";
}


var a_lang = document.getElementById("list-lang").getElementsByTagName("a");
for (var i in a_lang){
    a_lang[i].onclick = function(event){
        choose_lang.innerHTML = this.innerHTML;
        lang.style.overflow = "hidden";
        event.stopPropagation()
        return false;
    }
}
