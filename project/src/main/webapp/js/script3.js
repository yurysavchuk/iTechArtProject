window.onload = getPoint;

var id_files = 0;

function getPoint() {

    document.getElementById('editNumber').onclick = editNumberPopup;
    document.getElementById('createNumber').onclick = createNumberPopup;
    document.getElementById('close').onclick = closeNumberPopup;
    document.getElementById('saveNmbr').onclick = savePhone;
    document.getElementById('createFile').onclick = popupFilesCreate;
    document.getElementById('editFile').onclick = popupFilesEdit;
    document.getElementById('closeF').onclick = closeFilePopup;
    document.getElementById('cancelNmbr').onclick = closeNumberPopup;
    document.getElementById("cancelAttach").onclick = closeFilePopup;
    document.getElementById("deletePhone").onclick = deleteNumber;

    document.getElementById("headImg").onclick = createPhotoPopup;
    document.getElementById("closePhotoPopup").onclick = closePhotoPopup;
    document.getElementById("cancelPhoto").onclick = closePhotoPopup;
    document.getElementById("createContact").onclick = createContact;

    document.getElementsByName("countryCode")[0].onblur = validateCountryCode;
    document.getElementsByName('operCode')[0].onblur = validateOperCode;
    document.getElementsByName('number')[0].onblur = validateNumber;


    var countRowsPhone = document.getElementById("phoneTables").rows.length - 1;

    var listPhone = document.getElementsByName("rowPhone");

    var listHidden = document.getElementsByName("tdHiddenElement");

    var listCheckBoxPhone = document.getElementsByName("checkPhone");
    var j = 1;
    for (var i = 0; i < countRowsPhone; i++) {
        listPhone[i].id = "rowPhone" + j;
        listPhone[i].cells[0].setAttribute("id", j);
        listPhone[i].cells[1].setAttribute("name", "number" + j);
        listPhone[i].cells[2].setAttribute("name", "type" + j);
        listPhone[i].cells[3].setAttribute("name", "comment" + j);

        listHidden[i].firstElementChild.name = "phone";
        listCheckBoxPhone[i].setAttribute("id", j);
        j += 1;
    }

    var listFile = document.getElementsByName("rowFile");
    var countRowsFile = document.getElementById("fileTable").rows.length - 1;
    var listHidden = document.getElementsByName("tdFileHidden");
    var listCheckBox = document.getElementsByName("checkFile");
    var j = 1;
    for (var i = 0; i < countRowsPhone; i++) {
        listFile[i].id = "rowFile" + j;
        listFile[i].cells[0].setAttribute("id", j);
        listFile[i].cells[1].setAttribute("name", "filename" + j);
        listFile[i].cells[2].setAttribute("name", "dateLoad" + j);
        listFile[i].cells[3].setAttribute("name", "comfile" + j);
        listHidden[i].firstElementChild.name = "file";
        listCheckBox[i].setAttribute("id", j);
        j += 1;

    }
    document.getElementsByName("fileId")[0].value = 0;

}


function createContact(form) {

    if(!validateFormCreate()){
       return false;
    }else{
        return true;
    }

}

function createNumberPopup() {
    document.getElementById('b-popup').style.display = 'block';
    document.getElementById('b-popup-content').style.display = 'block';

    document.getElementsByName("countryCode")[0].value = "";
    document.getElementsByName("operCode")[0].value = "";
    document.getElementsByName("number")[0].value = "";
    document.getElementsByName("type")[0].value = "";
    document.getElementsByName("comment")[0].value = "";

    document.getElementsByName("idPhoneNumber")[0].value = "";
}

function createPhotoPopup() {

    document.getElementById('photo-popup').style.display = 'block';
    document.getElementById('photo-popup-content').style.display = 'block';

}

function closePhotoPopup() {
    document.getElementById('photo-popup').style.display = 'none';
    document.getElementById('photo-popup-content').style.display = 'none';
}

function editNumberPopup() {

    createNumberPopup();
    var ids = [];
    var j = 0;
    var checkboxList = document.getElementsByName("checkPhone");
    for (var i = 0; i < checkboxList.length; i++) {

        if (checkboxList[i].checked) {
            ids[j] = checkboxList[i].id;
            j++;
        }
    }
    if (j > 1) {

    }
    if (j == 1) {
        var id = ids[0];

        var number = document.getElementsByName("number" + id)[0].innerHTML;

        var type = document.getElementsByName("type" + id)[0].innerHTML;
        type = type.trim();

        var comment = document.getElementsByName("comment" + id)[0].innerHTML;
        comment = comment.trim();
        var phoneNumber = parsePhoneNumber(number);


        document.getElementsByName("countryCode")[0].value = phoneNumber[0];
        document.getElementsByName("operCode")[0].value = phoneNumber[1];
        document.getElementsByName("number")[0].value = phoneNumber[2];
        document.getElementsByName("type")[0].value = type;
        document.getElementsByName("comment")[0].value = comment;

        document.getElementsByName("idPhoneNumber")[0].value = id;

    }
}

function savePhone() {

    if (!validateNumberForm()) {
        return false;
    } else {
        var countryCode = document.getElementsByName("countryCode")[0].value.trim();

        var operCode = document.getElementsByName("operCode")[0].value.trim();

        var number = document.getElementsByName("number")[0].value.trim();

        var type = document.getElementsByName("type")[0].value.trim();

        var comment = document.getElementsByName("comment")[0].value.trim();

        var id_number = document.getElementsByName("idPhoneNumber")[0].value;

        if (id_number != 0) {
            document.getElementsByName("number" + id_number)[0].innerHTML = "+" + countryCode + "-" + operCode + "-" + number;
            document.getElementsByName("comment" + id_number)[0].innerHTML = comment;
            document.getElementsByName("type" + id_number)[0].innerHTML = type;
            var contentInp = document.getElementsByName("phone")[id_number - 1].value;
            var ind = contentInp.indexOf(',');
            var id_contact = contentInp.substring(0, ind);
            document.getElementsByName("phone")[id_number - 1].value = id_contact + "," + countryCode + "," + operCode + "," + number + "," + type + "," + comment;
        } else {

            var table = document.getElementById("phoneTables").getElementsByTagName('TBODY')[0];
            var row = document.createElement('tr');
            var id = document.getElementById("phoneTables").rows.length;
            row.id = "rowPhone" + id;
            table.appendChild(row);
            var tdCheck = document.createElement('td');
            var tdNumber = document.createElement('td');
            var tdType = document.createElement('td');
            var tdComment = document.createElement('td');
            var check = document.createElement('input');

            check.type = "checkbox";
            check.setAttribute("id", id);
            check.setAttribute("name", "checkPhone");
            tdCheck.appendChild(check);
            var hidInp = document.createElement('input');
            hidInp.type = "hidden";

            hidInp.value = "0," + countryCode + "," + operCode + "," + number + "," + type + "," + comment;
            hidInp.name = "phone";
            tdNumber.setAttribute("name", "number" + id);
            tdType.setAttribute("name", "type" + id);
            tdComment.setAttribute("name", "comment" + id);
            tdCheck.appendChild(hidInp);
            row.appendChild(tdCheck);
            row.appendChild(tdNumber);
            row.appendChild(tdType);
            row.appendChild(tdComment);
            tdNumber.innerHTML = "+" + countryCode + "-" + operCode + "-" + number;
            tdType.innerHTML = type;
            tdComment.innerHTML = comment;
        }

        closeNumberPopup();
    }

}



function deleteNumber() {
    var ids = [];
    var j = 0;
    var checkboxList = document.getElementsByName("checkPhone");

    for (var i = 0; i < checkboxList.length; i++) {

        if (checkboxList[i].checked) {
            ids[j] = checkboxList[i].id;
            j++;
        }
    }
    if (j > 1) {

    }
    if (j == 1) {
        var id = ids[0];

        var table = document.getElementById("phoneTables");
        var row = document.getElementById("rowPhone" + id);

        var inp = document.getElementsByName("phone");

        var str = inp[id - 1].value;

        var ind = str.charAt(',');

        if (ind != 0) {
            document.getElementById("deletedPhones").value += ind + ",";
        }
        table.deleteRow(row.rowIndex);

    }
}

function parsePhoneNumber(str) {

    str = str.trim();
    var index = str.charAt("+");

    str = str.substring(index + 1);


    var ind = str.charAt('-');
    var countryCode = str.substring(0, ind);

    var ind2 = str.lastIndexOf("-");
    ind++;
    var operCode = str.substring(ind, ind2);

    var number = str.substring(ind2 + 1);

    var number = new Array(countryCode, operCode, number);

    return number;
}

function closeNumberPopup() {

    document.getElementById('b-popup').style.display = 'none';
    document.getElementById('b-popup-content').style.display = 'block';

}

function popupFilesCreate() {
    document.getElementById('file-popup').style.display = 'block';
    document.getElementById('file-popup-content').style.display = 'block';


    document.getElementsByName("commentFile")[0].value = "";

    var inp = document.getElementById("fileUpload");

}


function popupFilesEdit() {
    popupFilesCreate();
    var ids = [];
    var j = 0;
    var checkboxList = document.getElementsByName("checkFile");

    for (var i = 0; i < checkboxList.length; i++) {

        if (checkboxList[i].checked) {
            ids[j] = checkboxList[i].id;
            j++;
        }
    }
    if (j > 1) {



    }
    if (j == 1) {
        var id = ids[0];

        var file = document.getElementsByName("filename1")[0];

        var filename = document.getElementsByName("filename" + id)[0].innerHTML;
        filename = filename.trim();



        var comment = document.getElementsByName("comfile" + id)[0].innerHTML;
        comment = comment.trim();

        document.getElementById("fileformlabel").innerHTML = filename;
        document.getElementsByName("commentFile")[0].value = comment;
        document.getElementsByName("fileId")[0].value = id;
    }
}


function closeFilePopup() {
    document.getElementById('file-popup').style.display = 'none';
    document.getElementById('file-popup-content').style.display = 'block';

    var inp = document.getElementById("file" + id_files);
    inp.className = "invisible";

    id_files++;

    document.getElementsByName("fileId")[0].value = "";
}

//validate functions

function testInput(container, input) {
    var reg = new RegExp("[0-9]+");
    var boll = reg.test(input.value.trim());

    resetError(container);
    if (boll) {
        input.focus();
        showError(container, "Please enter only letters!");
        return false;
    }
    else {
        return true;
    }
}


function showError(container, errorMessage) {
    var msgElem = document.createElement('span');
    msgElem.className = "error-message";
    msgElem.innerHTML = errorMessage;
    container.appendChild(msgElem);
}

function resetError(container) {
    if (container.lastChild.className == "error-message") {
        container.removeChild(container.lastChild);
    }
}

function validateName() {
    var pInput = document.getElementById('pName');
    var name = document.getElementById('crName');
    if (name.value.trim() == "") {
        name.focus();
        resetError(pInput);
        showError(pInput, "Please enter name!");
        return false;
    } else {
        return testInput(pInput, name);
    }

}

function validateSurname() {

    var pInput = document.getElementById('pSurname');
    var surname = document.getElementById('crtSurname');
    if (surname.value.trim() == "") {
        surname.focus();
        resetError(pInput);
        showError(pInput, "Please enter surname!");
        return false;
    }
    return testInput(pInput, surname);
}


function validateMName() {
    var pInput = document.getElementById('pMName');
    var mname = document.getElementById('crtMName');
    return testInput(pInput, mname);

}

function validateNationality() {
    var pInput = document.getElementById('pNation');
    var nation = document.getElementById('crtNation');
    return testInput(pInput, nation);
}

function validateMaritStat() {
    var pInput = document.getElementById('pMrtStat');
    var stat = document.getElementById('crtMrtStat');
    return testInput(pInput, stat);
}

function validateWorkplace() {
    var pInput = document.getElementById('pPlace');
    var place = document.getElementById('crtPlace');
    return testInput(pInput, place);
}

function validateCountry() {
    var pInput = document.getElementById('pCountry');
    var cntry = document.getElementById('crtCountry');
    return testInput(pInput, cntry);
}

function validateCity() {
    var pInput = document.getElementById('pCity');
    var city = document.getElementById('crtCity');
    return testInput(pInput, city);
}

function validateEmail() {

    var input = document.getElementById('pEmail');
    var email = document.getElementById('crtEmail');
    var emailValue = email.value.trim();

    var reg = new RegExp("([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}");
    var boll = reg.test(emailValue);

    resetError(input);
    if (emailValue == "") {
        return true;
    } else if (!boll) {
        email.focus();
        showError(input, "Please enter email in right form!");
        return false;
    } else {
        return true;
    }
}

function validateAddress() {
    var input = document.getElementById('pAddress');
    var address = document.getElementById('crtAddress');
    var addressValue = address.value.trim();

    var reg = new RegExp("[a-z|а-я]{2}.([A-Z]|[А-Я]){1}([a-z]|[а-я]){1,30},([h]|[д]){1}.[0-9]{1,3}[/a-z0-9]?,[0-9]{1,4}");


    var boll = reg.test(addressValue);
    resetError(input);
    if (addressValue == "") {
        return true;
    } else if (!boll) {
        address.focus();
        showError(input, "Please enter address in right form!");
        return false;
    } else {
        return true;
    }
}

function validateIndex() {
    var input = document.getElementById('pInd');
    var index = document.getElementById('crtIndex');
    var indexValue = index.value.trim();

    var reg = new RegExp("[a-z|?-?]+");
    var boll = reg.test(indexValue);
    resetError(input);
    if (indexValue == "") {
        return true;
    } else if (boll) {
        index.focus();
        showError(input, "Please enter index in right form!");
        return false;
    } else {
        return true;
    }
}

function validateDate() {
    var input = document.getElementById('pBirthday');
    var birthday = document.getElementById('crtBirthday');

    var reg = new RegExp("[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])");
    var boll = reg.test(birthday.value);
    resetError(input);
    if (!boll) {
        birthday.focus();
        showError(input, "Please enter date in right form!");
        return false;
    } else if (boll) {
        var now = new Date();

        var birthdate = new Date(birthday.value)

        if (now < birthdate) {

            birthday.focus();
            showError(input, "You was born in future? It is interesting...");
            return false;
        }
    }
    return true;

}

function validateSite() {
    var result = true;
    var input = document.getElementById('pSite');
    var site = document.getElementById('crtSite');

    var reg = new RegExp("www.[a-z.]*.[a-z]{2,3}");
    var siteValue = site.value.trim();

    var boll = reg.test(siteValue);

    resetError(input);
    if (siteValue == "") {

        return true;
    }
    else if (!boll) {
        site.focus();
        showError(input, "Please enter site name in right form!");
        result = false;
    }
}


function getName(str) {
    if (str.lastIndexOf('\\')) {
        var i = str.lastIndexOf('\\') + 1;
    }
    else {
        var i = str.lastIndexOf('/') + 1;
    }
    var filename = str.slice(i);
    var uploaded = document.getElementById("fileformlabel");
    uploaded.innerHTML = filename;
}
function getName2(str) {
    if (str.lastIndexOf('\\')) {
        var i = str.lastIndexOf('\\') + 1;
    }
    else {
        var i = str.lastIndexOf('/') + 1;
    }
    var filename = str.slice(i);
    var uploaded = document.getElementById("fileformlabel2");
    uploaded.innerHTML = filename;
}

function validateFormCreate() {

    if (!validateName()) {
        return false;
    }
    if (!validateSurname()) {
        return false;
    }
    if (!validateMName()) {
        return false;
    }
    if (!validateDate()) {
        return false;
    }
    if (!validateNationality()) {
        return false;
    }
    if (!validateMaritStat()) {
        return false;
    }
    if (!validateSite()) {
        return false;
    }
    if (!validateEmail()) {
        return false;
    }
    if (!validateWorkplace()) {
        return false;
    }
    if (!validateCountry()) {
        return false;
    }
    if (!validateCity()) {
        return false;
    }
    if (!validateAddress()) {
        return false;
    }
    if (!validateIndex()) {
        return false;
    }
    return true;

}

function validateNumberForm() {

    if (!validateCountryCode()) {
        return false;
    }
    if (!validateOperCode()) {
        return false;
    }
    if (!validateNumber()) {
        return false;
    } else {
        return true;
    }
}

function validateCountryCode() {
    var container = document.getElementById('pCountryCode');
    var input = document.getElementsByName('countryCode')[0];
    if (input.value == "") {
        resetErrorNumber(container);
        showErrorNumber(container, "This field cann't be empty!");
        return false;
    }
    return validateCode(container, input);
}

function validateOperCode() {
    var container = document.getElementById('pOperCode');
    var input = document.getElementsByName('operCode')[0];
    if (input.value == "") {
        resetErrorNumber(container);
        showErrorNumber(container, "This field cann't be empty!");
        return false;
    }
    return validateCode(container, input);
}

function validateNumber() {
    var container = document.getElementById('pNumber');
    var input = document.getElementsByName('number')[0];
    if (input.value == "") {
        resetErrorNumber(container);
        showErrorNumber(container, "This field cann't be empty!");
        return false;
    }
    return validateCode(container, input);
}

function validateCode(container, input) {
    var inputValue = input.value.trim();

    var reg = new RegExp("[a-z|?-?]+");
    var boll = reg.test(inputValue);
    resetErrorNumber(container);
    if (inputValue == "") {
        return true;
    } else if (boll) {
        input.focus();
        showErrorNumber(container, "Please enter index in right form!");
        return false;
    } else {
        return true;
    }
}


function resetErrorNumber(container) {
    if (container.lastChild.className == "error-message-number") {
        container.removeChild(container.lastChild);
    }
}


function showErrorNumber(container, errorMessage) {
    var msgElem = document.createElement('span');
    msgElem.className = "error-message-number";
    msgElem.innerHTML = errorMessage;
    container.appendChild(msgElem);
}