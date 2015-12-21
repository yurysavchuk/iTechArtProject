window.onload = getPoint;
function getPoint() {
    document.getElementById('emailForm').onsubmit = validateForm;
}

function validateForm(form) {

    var emailList = document.getElementsByName("idEmail");
    var j = 0;
    for(var i = 0; i<emailList.length; i++){
        if(emailList[i].innerHTML.trim()!=""){
            j++;
        }
    }
    if (j >= 1) {
        return true;
    } else {
        var container = document.getElementById("errorMessage");
        resetError(container);
        var message = document.createElement('span');
        message.innerHTML = "Contact must have email!"
        message.className =  "error-message";
        container.appendChild(message);
        return false;
    }

}

function resetError(container) {
    if (container.lastChild.className == "error-message") {
        container.removeChild(container.lastChild);
    }
}