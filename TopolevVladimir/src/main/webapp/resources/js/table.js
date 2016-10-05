

<!--Script for checkbox-->
var rootPath = window.location.protocol + "//" + window.location.host
    + window.location.pathname.replace("/contactlist", "");


var listCheckbox = document.getElementsByTagName("table")[0]
    .getElementsByClassName("delete-by-id");

for(var i in listCheckbox){
    listCheckbox[i].onchange = function(){
        var tr = this.parentNode.parentNode.parentNode;
        if (tr.getAttribute("class") == "checked"){
            tr.removeAttribute("class");
        } else {
            tr.setAttribute("class", "checked");
        }
    }
}



document.getElementById("count-row").onchange = function(event) {
    window.location.replace(rootPath + "/contactlist?countRow="
        + this.value);
};

function getCheckedId() {
    arrayId = [];
    for (var i = 0; i < listCheckbox.length; i++) {
        if (listCheckbox[i].checked)
            arrayId.push(listCheckbox[i].value);
    }
    return arrayId;
}

document.getElementById("delete-contact").onclick = function(event) {
    var checkedId = getCheckedId();
    if (checkedId.length > 0) {
        window.location.replace(rootPath + "/contactdelete?delete="
            + JSON.stringify(checkedId) + "&page=" + page + "&countRow=" + countRow);
    } else{
        alert("You have to choose one contact at least.")
    }

};

document.getElementById("send-messages").onclick = function(){
    var checkedId = getCheckedId();
    if (checkedId.length > 0){
        window.location.replace(rootPath + "/sendmessage?sendto=" + JSON.stringify(checkedId));
    } else{
        alert("You have to choose one contact at least.")
    }
    return false;
}


document.getElementById("delete-all").onclick = function(){
    var position = this.checked;
    for (var i = 0; i < listCheckbox.length; i++) {
        listCheckbox[i].checked = position;
    }
    for(var i = 0; i < listCheckbox.length; i++){
        if (listCheckbox[i].checked == true){
            listCheckbox[i].parentNode.parentNode.parentNode.setAttribute("class", "checked");
        } else{
            listCheckbox[i].parentNode.parentNode.parentNode.removeAttribute("class");
        }
    }
}


var showpopup = document.getElementById("show-delete-popup");

var popup_overlay = document.getElementById("modal");

showpopup.onclick = function(){
    popup_overlay.style.display = "block";
    return false;
}
var but_close_modal = document.getElementById("close-modal");
but_close_modal.onclick = function(){
    popup_overlay.style.display = "none";
    return false;
}

