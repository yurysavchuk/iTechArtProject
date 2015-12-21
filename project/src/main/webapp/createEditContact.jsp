<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
    <link href="css/createEdit.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" type="text/css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link>
    <script src="js/script3.js">

    </script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <p>Create/Edit contact</p>
    </div>

    <div class="linkDiv">
        <p>
            <a href="viewAllContacts.do">Contacts list</a>
            <a href="searchForm.jsp">Search contact</a>
        </p>
    </div>
    <div id="content" class="edit">
        <div id="photoDiv">

            <c:choose>
                <c:when test="${empty contact.pathToImg}">
                    <p>
                        <img src="/contacts/images/siluet.png" class="avaImg" id="headImg">
                    </p>
                </c:when>
                <c:otherwise>
                    <p>
                        <img src="${contact.pathToImg}" class="avaImg" id="headImg">
                    </p>>
                </c:otherwise>
            </c:choose>

        </div>
        <form method="post" action="create.do" enctype="multipart/form-data"
              id="createForm">
            <%--<form method="post" action="create.do">--%>
            <input type="hidden" name="id_contact" value="${contact.id}"/>
            <input type="hidden" name="id_address" value="${contact.address.id}"/>
            <input type="hidden" value="${contact.pathToImg}" name="pathToImg">


            <p id="pName"><label>Name*</label>
                <input type="text" name="name" class="inp form-control "
                       id="crName" onblur="validateName()"
                       value="${contact.name}" maxlength="30">

            </p>


            <p id="pSurname">
                <label float=left>Surname*</label>
                <input type="text" name="surname" class="form-control inp" id="crtSurname" onblur="validateSurname()"
                       value="${contact.surname}" maxlength="30">
            </p>

            <p id="pMName">
                <label>Middle name</label>
                <input type="text" name="mName" class="inp form-control" id="crtMName" onblur="validateMName()"
                       value="${contact.midleName}" maxlength="30">
            </p>

            <p id="pBirthday">
                <label>Birthday*</label>
                <input type="text" name="birthday" class="inp form-control" id="crtBirthday" onblur="validateDate()"
                       value="${contact.birthday}" maxlength="10" placeholder="YYYY-MM-DD">
            </p>

            <p>
                <label>Gender</label>
                <select name="sex" class="inp form-control">
                    <option value="male">male</option>
                    <option value="female">female</option>
                </select>
            </p>
            <p id="pNation">
                <label>Nationality</label>
                <input type="text" name="nationality" class="inp form-control" id="crtNation"
                       onblur="validateNationality()"
                       value="${contact.nationality}" maxlength="20"></p>

            <p id="pMrtStat">
                <label>Marital status</label>
                <input type="text" name="mrtlStat" class="inp form-control" id="crtMrtStat" onblur="validateMaritStat()"
                       value="${contact.maritStat}" maxlength="20"></p>

            <p id="pSite">
                <label>Web Site</label>
                <input type="text" name="webSite" class="inp form-control" id="crtSite" onblur="validateSite()"
                       value="${contact.webSite}" maxlength="30">
            </p>

            <p id="pEmail">
                <label>Email</label>
                <input type="email" name="email" class="inp form-control" id="crtEmail" onblur="validateEmail()"
                       value="${contact.email}" maxlength="30">
            </p>

            <p id="pPlace">
                <label>Current workplace</label>
                <input type="text" name="workplace" class="inp form-control" id="crtPlace" onblur="validateWorkplace()"
                       value="${contact.curWorkplace}">
            </p>

            <p>
                <label>Address</label>
            </p>

            <p id="pCountry">
                <label class="adrLbl">Country</label>
                <input type="text" name="country" class="address form-control" id="crtCountry"
                       onblur="validateCountry()"
                       value="${contact.address.country}" maxlength="30">
            </p>

            <p id="pCity">
                <label class="adrLbl">City</label>
                <input type="text" name="city" class="address form-control" id="crtCity" onblur="validateCity()"
                       value="${contact.address.city}" maxlength="30">
            </p>

            <p id="pAddress">
                <label class="adrLbl">Street/House/Flat</label>
                <input type="text" name="address" class="address form-control" id="crtAddress"
                       onblur="validateAddress()"
                <c:choose>
                <c:when test="${ contact.address.street!=null && not empty contact.address.street} ">
                       value="st.${contact.address.street},${contact.address.house},${contact.address.flat}"
                </c:when>
                </c:choose>
                       maxlength="65" placeholder="ул.***,д.*,**">
            </p>

            <p id="pInd">
                <label class="adrLbl">Index</label>
                <input type="text" name="index" class=" form-control address" id="crtIndex" onblur="validateIndex()"
                       value="${contact.address.index}" maxlength="10">
            </p>


            <div class="fileTableDiv">
                <table id="fileTable" class="table table-hover ">

                    <p class="tableButton">
                        <input type="button" value="create" id="createFile" class="btn btn-success">
                        <input type="button" value="edit" class="btn btn-success" id="editFile">
                        <input type="submit" value="delete" class="btn btn-success" id="deleteFil" name="command">
                    </p>

                    <input type="hidden" name="deletedFiles" value="${deletedFiles}" id="deletedFiles"/>

                    <tr>
                        <td></td>
                        <td>Имя файла присоединения</td>
                        <td>Дата загрузки</td>
                        <td>Коментарий</td>
                    </tr>
                    <c:forEach var="file" items="${contact.listFile}">
                        <tr name="rowFile">
                            <td name="tdFileHidden">
                                <input type="hidden" value="${file.id},${file.path},${file.filename},
                                        ${file.dateLoad},${file.comment}" name="file"/>
                                <input type="checkbox" name="checkFile" id=${file.id} value="${file.path}">
                            </td>


                            <td>
                                    ${file.filename}
                            </td>
                            <td>
                                    ${file.dateLoad}
                            </td>
                            <td>
                                    ${file.comment}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="fileTableDiv">
                <table class="table" id="phoneTables">
                    <p class="tableButton">
                        <input type="button" value="create" class="btn btn-success" id="createNumber"/>
                        <input type="button" value="edit" class="btn btn-success" id="editNumber" name="submit"/>
                        <input type="button" value="delete" class="btn btn-success" id="deletePhone"/>
                    </p>

                    <input type="hidden" name="deletedPhones" value="${deletedPhones}" id="deletedPhones"/>

                    <tr>
                        <td>

                        </td>
                        <td>Полный номер</td>
                        <td>Описание номера</td>
                        <td>Комментарий</td>
                    </tr>
                    <c:forEach var="number" items="${contact.phoneNumbers}">
                        <tr name="rowPhone">
                            <td name="tdHiddenElement">
                                <input type="hidden"
                                       value="${number.id},${number.countryCode},${number.operCode},${number.number},
                                       ${number.type},${number.comment}" name="phone"/>
                                <input type="checkbox" name="checkPhone" id="<%--${number.id}--%>">
                            </td>
                            <td>
                                +${number.countryCode}-${number.operCode}-${number.number}
                            </td>
                            <td>
                                    ${number.type}
                            </td>
                            <td>
                                    ${number.comment}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div id="file-popup" class="b-popup">
                <div id="file-popup-content" class="b-popup-content">
                    <div class="popupExit">
                        <p>
                            <input type="button" value="close" id="closeF" class="closeBtn"/>
                        </p>
                    </div>

                    <div class="popupCntnt">
                        <div id="popupFileSave"></div>
                        <input type="hidden" value="" name="fileId">

                        <label class="lblPopup">File name</label>

                        <div class="fileform2">

                            <div id="fileformlabel">
                            </div>
                            <div class="selectbutton">Обзор</div>
                            <input type="file" name="upload" id="upload" onchange="getName(this.value);"/>
                        </div>

                        <p>
                            <label class="lblPopup">Comment</label>
                            <textarea name="commentFile" class="txtAreaPopup"></textarea>
                        </p>
                    </div>
                    <div class="filePopupBtns">
                        <p>
                            <input type="submit" value="save" id="saveAttach" class="btn btn-success" name="command"/>
                            <input type="button" value="cancel" id="cancelAttach" class="btn btn-success"/>
                        </p>
                    </div>
                </div>
            </div>


            <div id="photo-popup" class="photo-popup">
                <div id="photo-popup-content" class="photo-popup-content">
                    <div class="popupExit">
                        <p>
                            <input type="button" value="close" id="closePhotoPopup" class="closeBtn"/>
                        </p>
                    </div>

                    <div class="popupCntnt">
                        <input type="hidden" value="" name="photoId">

                        </p>

                        <div class="fileform">
                            <div id="fileformlabel2">
                            </div>
                            <div class="selectbutton">Обзор</div>
                            <input type="file" name="upload" id="upload" onchange="getName2(this.value);"/>
                        </div>

                        </p>
                    </div>
                    <div class="photoPopupButtons">
                        <p>
                            <input type="submit" value="savePhoto" id="savePhoto" class="btn btn-success"
                                   name="command"/>
                            <input type="button" value="cancel" id="cancelPhoto" class="btn btn-success"/>
                        </p>
                    </div>

                </div>
            </div>

            <input id="createContact" type="submit" value="create" class="btn btn-success" name="command">
        </form>
    </div>

    <div id="footer">

    </div>

    <div id="b-popup" class="number-popup" dsplay="">
        <div id="b-popup-content" class="number-popup-content">
            <div class="popupExit">
                <p>
                    <input type="button" value="close" id="close" class="closeBtn"/>
                </p>
            </div>
            <form action="numbersTable.do" name="numbersTable" method="get">
                <div class="popupCntntPhone" id="popupCountPhone">
                    <input type="hidden" name="idPhoneNumber" value="">

                    <p id="pCountryCode">
                        <label class="lblPopup">Код страны</label>
                        <input type="text" name="countryCode" class="inpPopupPhone form-control"/>

                    </p>

                    <p id="pOperCode">
                        <label class="lblPopup">Код оператора</label>
                        <input type="text" name="operCode" class="inpPopupPhone form-control"/>
                    </p>

                    <p id="pNumber">
                        <label class="lblPopup">Телефонный номер</label>
                        <input type="text" name="number" class="inpPopupPhone form-control"/>
                    </p>

                    <p>
                        <label class="lblPopup">Тип телефона</label>
                        <select size="1" class="inpPopupPhone form-control" name="type">
                            <option value="mobile">Mobile</option>
                            <option value="home">Home</option>
                        </select>
                    </p>
                    <p id="pComment">
                        <label class="lblPopup">Коментарий</label>
                        <textarea name="comment" class="txtAreaPopup"></textarea>

                    </p>
                </div>
                <div class="popupBtnsPhone">
                    <p>
                        <input type="button" value="save" id="saveNmbr" class="btn btn-success"/>
                        <input type="button" value="cancel" id="cancelNmbr" class="btn btn-success" onclick="closeN()"/>
                    </p>
                </div>
            </form>
        </div>

    </div>


</div>

</body>
</html>
