function Table(idBlock, increment, callBackFullTr, clearModal, editModal){
    var self = this;

    var blockTable = document.getElementById(idBlock);
    var bodyTable = blockTable.getElementsByTagName("tbody")[0];

    var buttonCreateNewRow = blockTable.getElementsByClassName("create-new-row")[0];
    var buttonDeleteRows = blockTable.getElementsByClassName("delete-rows")[0];
    var buttonEditRow = blockTable.getElementsByClassName("edit-row")[0];

    var popupWindow = document.getElementById(idBlock + "-modal");
    var buttonCloseModal = popupWindow.getElementsByClassName("close-modal")[0];
    var buttonCreateOrEditModal = popupWindow.getElementsByClassName("create-edit")[0];

    var typePressButton = undefined;
    var choose_edit_row = undefined;
    var availableIndexes = [];
    var deleteId = [];

    this.setAvailableIndexes = function(array){
        availableIndexes = array;
        self.setValueInputById(idBlock + "-indexes",JSON.stringify(availableIndexes));
    }
    buttonCreateNewRow.onclick = function(){
        clearModal.apply(self);
        popupWindow.style.display = "block";
        typePressButton = "create";
        return false;
    }

    buttonEditRow.onclick = function(){
        var choose_checkbox = getChooseCheckbox();
        if (choose_checkbox != undefined){
            editModal.apply(self, [choose_checkbox]);
            popupWindow.style.display = "block";
            typePressButton = "edit";
        }
        return false;
    }

    //
    deleteValueFromArray = function(array, value){
        for(var i=0; i<array.length; i++){
            if (array[i] == value){
                array.splice(i,1);
                return;
            }
        }
    }


    buttonDeleteRows.onclick = function(){
        var listCheckbox = getListCheckBox();
        var checked_checkbox = [];
        for (var i = 0; i< listCheckbox.length; i++){
            if (listCheckbox[i].checked) {
                var parent = listCheckbox[i].parentNode.parentNode.parentNode;
                var id = self.getValueHiddenInputInParent(parent,"id");
                if (id != undefined){
                    deleteId.push(id);
                };
                checked_checkbox.push(parent);
                deleteValueFromArray(availableIndexes, self.getValueHiddenInputInParent(parent,"inc"));
            }
        }
        self.setValueInputById(idBlock + "-indexes",JSON.stringify(availableIndexes));
        self.setValueInputById(idBlock + "-delete", JSON.stringify(deleteId));
        console.log(availableIndexes)
        while(checked_checkbox.length != 0){
            bodyTable.removeChild(checked_checkbox.pop());
        }
        return false;
    }

    buttonCloseModal.onclick = function(){
        popupWindow.style.display = "none";
        return false;
    }

    buttonCreateOrEditModal.onclick = function(){
        if (typePressButton == "create") createNewRow();
        if (typePressButton == "edit") editCurrentRow();
        return false;
    }

    function createNewRow(){
        var tr = document.createElement("tr");
        availableIndexes.push(++increment);
        self.setValueInputById(idBlock + "-indexes",JSON.stringify(availableIndexes))
        console.log(availableIndexes)
        callBackFullTr.apply(self, [tr, increment]);
        bodyTable.appendChild(tr);
        popupWindow.style.display = "none";
    }

    function editCurrentRow(){
        var choose_checkbox = getChooseCheckbox();
        if (choose_checkbox != undefined){
            var tr = choose_checkbox.parentNode.parentNode.parentNode;
            var inc = self.getValueHiddenInputInParent(tr,"inc");
            tr.removeAttribute("class");
            callBackFullTr.apply(self, [tr, inc]);
        }
        popupWindow.style.display = "none";
    }



    blockTable.getElementsByClassName("delete-all")[0].onclick = function(){
        var position = this.checked;
        var listCheckbox =getListCheckBox();
        for (var i = 0; i < listCheckbox.length; i++) {
            listCheckbox[i].checked = position;
            if (listCheckbox[i].checked == true){
                listCheckbox[i].parentNode.parentNode.parentNode.setAttribute("class", "checked");
            } else{
                listCheckbox[i].parentNode.parentNode.parentNode.removeAttribute("class");
            }
        }
        doDisabledButttonEdit();
    }

    function getListCheckBox(){
        return bodyTable.getElementsByClassName("checkbox");
    }

    function getChooseCheckbox(){
        var listCheckbox = getListCheckBox();
        for (var i = 0; i < listCheckbox.length; i++) {
            if (listCheckbox[i].checked) return listCheckbox[i];
        }
    }

    this.getValueInputById = function(id){
        var value = document.getElementById(id).value;
        document.getElementById(id).value = "";
        return value;
    }

    this.setValueInputById = function(id, value){
        document.getElementById(id).value = value;
    }

    this.getValueRadioByClass = function(nameClass){
        var radio = document.getElementsByClassName(nameClass);
        for (var i = 0; i < radio.length; i++){
            if (radio[i].checked) return radio[i].value;
        }
    }

    this.setValueRadioByClass = function(nameClass, value){
        var radio = document.getElementsByClassName(nameClass);
        for (var i = 0; i < radio.length; i++){
            if (radio[i].value == value) radio[i].checked = true;
        }
    }

    this.createHiddenInput = function(name, value){
        var input = document.createElement("input");
        input.setAttribute('type','hidden');
        input.setAttribute('name',name);
        input.setAttribute('value',value);
        return input;
    }

    this.getValueRadioByClass = function(nameClass){
        var radio = document.getElementsByClassName(nameClass);
        for (var i = 0; i < radio.length; i++){
            if (radio[i].checked) return radio[i].value;
        }
    }
    this.getValueHiddenInputInParent = function(parent, name){
        var input = parent.getElementsByTagName("input");
        var regexp = new RegExp(name);
        for (var i = 0; i<input.length; i++){
            if ((input[i].getAttribute("type") == "hidden")
                && (input[i].getAttribute("name").search(regexp) != -1)){
                return input[i].value;
            }
        }
    }
    function doDisabledButttonEdit(){
        var listCheckbox = getListCheckBox();
        var countChecked = 0;
        for (var i = 0; i< listCheckbox.length; i++){
            if (listCheckbox[i].checked) countChecked++;
        }
        if (countChecked > 1) buttonEditRow.setAttribute("disabled","disabled");
        else buttonEditRow.removeAttribute("disabled");
    }

    this.changeCheckRow = function(event){
        if (event != undefined){
            if (event.target.checked) event.target.parentNode.parentNode.parentNode.setAttribute("class", "checked");
            else event.target.parentNode.parentNode.parentNode.removeAttribute("class");
        }

        doDisabledButttonEdit();
        return false;
    }

}







/**/
var callBackCreateTr = function(tr, increment){
    var id = this.getValueHiddenInputInParent(tr,"id");
    console.log(id);

    var countrycode = this.getValueInputById("country-code");
    var operatorcode = this.getValueInputById("operator-code");
    var phonenumber = this.getValueInputById("phone-number");
    var typephone = this.getValueRadioByClass("phone-radio");
    var description = this.getValueInputById("description");
    tr.innerHTML = "";
    tr.innerHTML += "<td><div class='wrap-checkbox'>"+
        "<input type='checkbox' class='checkbox checkbox-phone'" +
        " onchange='tabelPhone.changeCheckRow(event)' value='" + increment + "'>" +
        "<label></label></div>"+
        "</td>";
    tr.innerHTML += "<td>+" + countrycode + "-" + operatorcode + "-" + phonenumber + "</td>";
    tr.innerHTML += "<td>" + typephone + "</td>";
    tr.innerHTML += "<td>" + description +"</td>";

    if (id != undefined){
        tr.appendChild(this.createHiddenInput("phone" + increment + ".id" ,id));
    }
    tr.appendChild(this.createHiddenInput("phone" + increment + ".inc" , increment));
    tr.appendChild(this.createHiddenInput("phone" + increment + ".countryCode" , countrycode));
    tr.appendChild(this.createHiddenInput("phone" + increment + ".operatorCode", operatorcode));
    tr.appendChild(this.createHiddenInput("phone" + increment + ".phoneNumber", phonenumber));
    tr.appendChild(this.createHiddenInput("phone" + increment + ".typePhone", typephone));
    tr.appendChild(this.createHiddenInput("phone" + increment + ".description", description));
}

var clearPhoneModal = function(){
    this.setValueInputById("country-code","");
    this.setValueInputById("operator-code","");
    this.setValueInputById("phone-number","");
    this.setValueInputById("description","");
    this.setValueRadioByClass("phone-radio", "Home")
}

var editPhoneModal = function(choose_checkbox){

        var parent = choose_checkbox.parentNode.parentNode.parentNode;
        //choose_edit_phone = getValueHiddenInputInParent(parent,"inc");
        var typephone = this.getValueHiddenInputInParent(parent, "typePhone");
        var countrycode = this.getValueHiddenInputInParent(parent, "countryCode");
        var operatorcode = this.getValueHiddenInputInParent(parent, "operatorCode");
        var phonenumber = this.getValueHiddenInputInParent(parent, "phoneNumber");
        var description = this.getValueHiddenInputInParent(parent, "description");
        this.setValueInputById("country-code",countrycode);
        this.setValueInputById("operator-code",operatorcode);
        this.setValueInputById("phone-number",phonenumber);
        this.setValueInputById("description",description);
        this.setValueRadioByClass("phone-radio", typephone);
}



var tabelPhone = new Table("phone", initAutoincrement,callBackCreateTr, clearPhoneModal, editPhoneModal);
tabelPhone.setAvailableIndexes(initAvailableIndexes);