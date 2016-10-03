/**
 * Created by Vladimir on 03.10.2016.
 */
document.getElementById("birthdaymore").onchange = function(){
    var date1 = new Date(this.value);
    var date2Str = document.getElementById("birthdayless").value;
    if (date2Str != ""){
        date2 = new Date(date2Str);
        if (date1 > date2){
            this.parentNode.parentNode.classList.add("has-error");
        } else{
            this.parentNode.parentNode.classList.remove("has-error");
        }
    }
}

document.getElementById("birthdayless").onchange = function(){
    var date1 = new Date(this.value);
    var date2Str = document.getElementById("birthdaymore").value;
    if (date2Str != ""){
        date2 = new Date(date2Str);
        if (date1 < date2){
            this.parentNode.parentNode.classList.add("has-error");
        } else{
            this.parentNode.parentNode.classList.remove("has-error");
        }
    }
}

document.getElementById("search").onclick = function(){
    var date1Str = document.getElementById("birthdaymore").value;
    var date2Str = document.getElementById("birthdayless").value;
    if (date1Str != "" && date2Str !=""){
        date1 = new Date(date1Str);
        date2 = new Date(date2Str);
        if (date1 > date2){
            document.getElementById("birthday").classList.add("has-error")
            return false;
        }
    }

}