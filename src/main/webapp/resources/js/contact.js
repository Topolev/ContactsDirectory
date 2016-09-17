function Table(idBlock, increment, callBackFullTr, clearModal, editModal, closeModalInCreateMode){

    this.increment = increment;

    this.incChoosenRow = undefined;

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
            self.incChoosenRow = self.getValueHiddenInputInParent(choose_checkbox.parentNode.parentNode.parentNode,"inc")
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

                /*It belongs only tableAttachmnet (delete input-file)*/
                if (idBlock == "attachment"){
                    var inc = self.getValueHiddenInputInParent(parent,"inc");
                    document.getElementById("container-file").removeChild(self.getFileInput("file" + inc));
                }

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
        if (typePressButton == "create" && closeModalInCreateMode != undefined){
            closeModalInCreateMode.apply(self);
        }

        return false;
    }

    buttonCreateOrEditModal.onclick = function(){
        if (typePressButton == "create") createNewRow();
        if (typePressButton == "edit") editCurrentRow();
        return false;
    }

    function createNewRow(){
        var tr = document.createElement("tr");
        self.increment++;
        availableIndexes.push(self.increment);
        self.setValueInputById(idBlock + "-indexes",JSON.stringify(availableIndexes))
        console.log(availableIndexes)

        callBackFullTr.apply(self, [tr, self.increment]);
        bodyTable.appendChild(tr);
        popupWindow.style.display = "none";
    }

    function editCurrentRow(){
        var choose_checkbox = getChooseCheckbox();
        if (choose_checkbox != undefined){
            var tr = choose_checkbox.parentNode.parentNode.parentNode;
            var inc = self.getValueHiddenInputInParent(tr,"inc");
            self.incChoosenRow = inc;
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
    this.getFileInput = function(name){
        var fileInputs = document.getElementsByTagName("input");
        for (var i=0; i<fileInputs.length; i++){
            if (fileInputs[i].getAttribute("type") == "file" && fileInputs[i].getAttribute("name") == name) return fileInputs[i];
        }
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

    this.chooseFile = function(event){
        if (typePressButton == "create") {
            self.getFileInput("file" + (this.increment + 1)).click();
        }
        if (typePressButton == "edit"){
            console.log(this.incChoosenRow)
            self.getFileInput("file" + this.incChoosenRow).click();
        }
        /*console.log("chooseFile");
        if (typePressButton =="create"){
            console.log(typePressButton)
            console.log("inc" + increment)
            self.getFileInput("file" + increment).click();
        }
        if (typePressButton == "edit"){
            console.log(self.increment_choosen_row)
            this.getFileInput("file" + this.increment_choosen_row).click();

        }*/
    }

    this.changeFile = function(event){
        currentInputFile = event.currentTarget;
        document.getElementById("choosen-file").innerHTML = currentInputFile.value.split('/').pop().split('\\').pop();
    }

}







/*Callback function for table PHONE*/
var callBackCreateTr = function(tr, increment){
    var id = this.getValueHiddenInputInParent(tr,"id");


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
/*END Callback function for table PHONE*/

console.log("INCREMENET:" + initAutoincrement)
var tablePhone = new Table("phone", initAutoincrement,callBackCreateTr, clearPhoneModal, editPhoneModal);
tablePhone.setAvailableIndexes(initAvailableIndexes);







/**/
var callBackCreateAttachmentTr = function(tr, increment){
    console.log("CREATE ROW")
    console.log(increment)
    var id = this.getValueHiddenInputInParent(tr,"id");

    var namefile = this.getValueInputById("name-file");
    var commentfile = this.getValueInputById("comment-file");
    tr.innerHTML = "";
    tr.innerHTML += "<td><div class='wrap-checkbox'>"+
        "<input type='checkbox' class='checkbox checkbox-phone'" +
        " onchange='tableAttachment.changeCheckRow(event)' value='" + increment + "'>" +
        "<label></label></div>"+
        "</td>";

    var date = new Date();
    var day = (parseInt(date.getDate(), 10) < 10 ) ? ('0'+date.getDate()) : (date.getDate());
    var month = (parseInt(date.getMonth() + 1, 10) < 10 ) ? ('0'+date.getMonth()) : (date.getMonth());

    var dateStr =  date.getFullYear() + "-" + day+ "-" + month;

    tr.innerHTML += "<td>" + namefile+ "</td>";
    tr.innerHTML += "<td>" + dateStr + "</td>";
    tr.innerHTML += "<td>" + commentfile +"</td>";

    if (id != undefined){
        tr.appendChild(this.createHiddenInput("attachment" + increment + ".id" ,id));
    }
    tr.appendChild(this.createHiddenInput("attachment" + increment + ".inc" , increment));
    tr.appendChild(this.createHiddenInput("attachment" + increment + ".nameFile" , namefile));
    tr.appendChild(this.createHiddenInput("attachment" + increment + ".commentFile" , commentfile));
    tr.appendChild(this.createHiddenInput("attachment" + increment + ".dateFile" , dateStr));
}


var clearAttachmentModal = function(){
    this.setValueInputById("name-file","");
    this.setValueInputById("comment-file","");
    document.getElementById("choosen-file").innerHTML = "File isn't choosen";

    /*create new input file*/
    var container = document.getElementById("container-file");
    var inputfile = document.createElement("input");
    inputfile.setAttribute("type","file");
    inputfile.setAttribute("name", "file" + (this.increment + 1));
    inputfile.addEventListener("change", tableAttachment.changeFile);
    container.appendChild(inputfile);
}


var editAttachmentModal = function(choose_checkbox){
    var parent = choose_checkbox.parentNode.parentNode.parentNode;

    var id = this.getValueHiddenInputInParent(parent,"id");
    var namefile = this.getValueHiddenInputInParent(parent, "nameFile");
    var commentfile = this.getValueHiddenInputInParent(parent, "commentFile");
    var inc = this.incChoosenRow;

    if (this.getFileInput("file" + inc).value == ""){
        document.getElementById("choosen-file").innerHTML = namefile;
    } else{
        document.getElementById("choosen-file").innerHTML = this.getFileInput("file" + inc).value.split('/').pop().split('\\').pop();
    }

    this.setValueInputById("name-file",namefile);
    this.setValueInputById("comment-file",commentfile);

}

var closeAttachModalInCreateMode = function(){
    document.getElementById("container-file").removeChild(this.getFileInput("file" + (this.increment + 1)));

}


var tableAttachment = new Table("attachment", 0, callBackCreateAttachmentTr, clearAttachmentModal, editAttachmentModal, closeAttachModalInCreateMode);
//tablePhone.setAvailableIndexes([]);
console.log(tableAttachment)