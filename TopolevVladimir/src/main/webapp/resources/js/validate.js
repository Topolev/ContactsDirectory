function Validate(){
    var map = {
        isNotEmpty : {func: isNotEmpty, message: "Field can not be empty."},
        isNumber : {func: isNumber, message: "Field must consist of numbers only."},
        isEmail : {func: isEmail, message: "Invalid format email."},
        isDate : {func: isDate, message: "Invalid format date."},
        isChoosenFile: {func: isChoosenFile, message: "You must choose file."},
        isMaxLength: {func: isMaxLength, message: "Maximum length of field is 25 simbol."},
        isDateLessThenTodayDate:{func: isDateLessThenTodayDate, message: "Birthday can be less that today date."}
    };

    var isEmailRE = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var isNumberRE = /^\d+$/;
    var isUrlRE = /^((https?|ftp)\:\/\/)?([a-z0-9]{1})((\.[a-z0-9-])|([a-z0-9-]))*\.([a-z]{2,6})(\/?)$/;

    function isNotEmpty(value){
        return !(value.trim() == "");
    }

    function isEmail(value){
        if (value.trim() == "") return true;
        return isEmailRE.test(value.trim());
    }
    function isNumber(value){
        if (value.trim() == "") return true;
        return isNumberRE.test(value);
    }
    function isUrl(value){
        return isUrlRE.test(value.trim());
    }
    function isDate(value){
        return  !(value.trim() == "");
    }
    function isDateLessThenTodayDate(value){
        if (value.trim() != ""){
            var date = new Date(value.trim());
            var now = new Date();
            return (date <= now);
        } else return true;
    }
    function isMaxLength(value){
        return (value.trim().length < 25);
    }
    function isChoosenFile(value){
        return (value != "File isn't choosen");
    }

    this.validateField = function(event, validators, idBlock){
        event = event || window.event;
        var field = event.currentTarget;
        var value;
        if (field.tagName == "INPUT"){
            value = field.value.trim();
        } else{
            value = field.innerHTML.trim();
        }

        var buttonLock = document.getElementById(idBlock);

        var isValid = true;
        for (var i= 0; i < validators.length; i++){
            if (!map[validators[i]].func(value)) {
                field.nextElementSibling.innerHTML = map[validators[i]].message;
                field.parentNode.classList.add("has-error");
                isValid = false;
                buttonLock.setAttribute("disabled", "disabled");
            };
        }
        if (isValid) {
            field.parentNode.classList.remove("has-error");
            buttonLock.removeAttribute("disabled") ;
        }
    }

    this.validateFieldList = function(mapForm){
        var isValidForm = true;

        for (var i=0; i < mapForm.length; i++){

            var field = document.getElementById(mapForm[i].id);
            var value;
            if (field.tagName == "INPUT"){
                value = field.value.trim();
            } else{
                value = field.innerHTML.trim();
            }


            for ( var j = 0; j < mapForm[i].validators.length; j++){
                if (!map[mapForm[i].validators[j]].func(value)){
                    field.nextElementSibling.innerHTML = map[mapForm[i].validators[j]].message;
                    field.parentNode.classList.add("has-error");
                    isValidForm = false;
                    break;
                } else{
                    field.parentNode.classList.remove("has-error");
                }
            }
        }

        return isValidForm;
    }
}
