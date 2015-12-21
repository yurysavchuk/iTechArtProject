window.onload = getPoint;
function getPoint() {
    document.getElementById('formList').onsubmit = validateForm;
}

function validateForm(form) {

    var j = 0;
    var checkboxList = document.getElementsByName("idContact");

    for (var i = 0; i < checkboxList.length; i++) {

        if (checkboxList[i].checked) {
            j++;
        }
    }
    if (j >= 1) {
        return true;
    } else {
        var container = document.getElementById("errorMessage");
        resetError(container);
        var message = document.createElement('span');
        message.innerHTML = "You must select one more element!"
        message.className = "error-mess";
        container.appendChild(message);
        return false;
    }

}

function resetError(container) {
    if (container.lastChild.className == "error-mess") {
        container.removeChild(container.lastChild);
    }
}