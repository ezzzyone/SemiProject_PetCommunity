const sellfileAdd = document.getElementById("sellfileAdd");
const imgDeleteBtn = document.querySelectorAll(".imgDeleteBtn");
const sellFileImages = document.getElementById("sellFileImages");
const realsellfileAdd = document.getElementById("realsellfileAdd");
const inputDiv = document.getElementById("inputDiv");
const imgDiv = document.getElementById("imgDiv");
const uFileDelete = document.querySelectorAll(".uFileDelete");
const itemName = document.getElementById("itemName");
const itemPrice = document.getElementById("itemPrice");
const itemContents = document.getElementById("itemContents");
const itemZipCode = document.getElementById("itemZipCode");
const itemAddress = document.getElementById("itemAddress");
const itemDeAddress = document.getElementById("itemDeAddress");
const itemCatg = document.getElementById("itemCatg");
const addItemBtn = document.getElementById("addItemBtn");
const addItemFrm = document.getElementById("addItemFrm");

let count=0;
let idx=0;

function setCount(c){
    if(c>=0){
        count=c;
    }
}


// add page: 이벤트 전달-------------------------------------------------------------
sellfileAdd.addEventListener("click", function(){
    console.log("fileAdd");
    if(count>4){
         alert("상품사진은 최대 5개까지만 추가 가능합니다");
         return;
    }

    const input = document.createElement("input");
    const inputType = document.createAttribute("type");
    inputType.value = "file";
    const inputId = document.createAttribute("id");
    inputId.value = "realsellfileAdd"+idx;
    console.log(inputId.value);
    const iname = document.createAttribute("name");
    iname.value = "files";
    const iClass = document.createAttribute("class");
    iClass.value = "inputNone"
    
    input.setAttributeNode(inputType);
    input.setAttributeNode(inputId);
    input.setAttributeNode(iname);
    input.setAttributeNode(iClass);
    
    inputDiv.appendChild(input);
    
    const child = document.getElementById(input.id);
    console.log("child : ", child);
    child.click();
});
//이벤트 전달 끝----------------------------------------------


//------------------------------------------------
// add page: 파일 추가 및 썸네일 이미지 생성 시작
sellFileImages.addEventListener("change", function(event){
    console.log("e",event.target);
    const reader = new FileReader();

    reader.onload = function(event) {

        const newImg = document.createElement("img");
        newImg.setAttribute("src", event.target.result);
        newImg.setAttribute("class", "thumnail");

        const newButton = document.createElement("button");
        const buttonStyle = document.createAttribute("type");
        buttonStyle.value = "button";

        const buttonClass = document.createAttribute("class");
        buttonClass.value="imgDeleteBtn";
        const buttonText = document.createTextNode("X");

        const buttonId = document.createAttribute("id");
        buttonId.value = idx;

        newButton.setAttributeNode(buttonStyle);
        newButton.setAttributeNode(buttonClass);
        newButton.setAttributeNode(buttonId);
        newButton.appendChild(buttonText);

        const newDiv = document.createElement("div");
        newDiv.setAttribute("class","newDiv");
        
        newDiv.appendChild(newImg);
        newDiv.appendChild(newButton);

        imgDiv.appendChild(newDiv);
        idx++;
        count++;
        };
        reader.readAsDataURL(event.target.files[0]);
});
//파일 추가 및 썸네일 이미지 생성 끝
//===================================================

// add page: 파일 삭제=======================================================
imgDiv.addEventListener("click", function(event){
    const df = event.target;
    let result = window.confirm("삭제하시겠습니까?")
      if(!result){
               return;
        }
    
    remId = document.getElementById("realsellfileAdd"+df.id);//인풋아이디
    remId.remove();  
    
    df.parentNode.remove();//버튼   
    count--;    
});

//삭제 끝------------------------------------------------

// add 조건 설정

addItemBtn.addEventListener("click", function(){
    let inResult = false;
    let ipResult = false;
    let imgResult = false;
    let izResult = false;
    let iaResult = false;
    let idaResult = false;
    let catResult = false;

    if(itemName.value.length<1){
        alert("상품명을 입력하세요");
        itemName.focus();
    } else{
        inResult = true;
    }

    if(itemPrice.value.length<1){
        alert("이용 가격을 입력하세요")
        itemPrice.focus();
    } else{
        ipResult = true;
    }

    if(inputDiv.childElementCount<1){
        alert("상품 사진을 추가하세요")
    } else{
        imgResult = true;
    }

    if(itemZipCode.value.length<1){
        alert("우편번호를 입력하세요")
        itemZipCode.focus();
    } else{
        izResult = true;
    }

    if(itemAddress.value.length<1){
        alert("주소를 입력하세요")
        itemAddress.focus();
    } else{
        iaResult = true;
    }

    if(itemDeAddress.value.length<1){
        alert("상세 주소를 입력하세요")
        itemDeAddress.focus();
    } else{
        idaResult = true;
    }

    if(itemCatg.value.length<1){
        alert("카테고리를 입력하세요")
        itemCatg.focus();
    } else{
        catResult = true;

    }

    if(inResult&&ipResult&&imgResult&&izResult&&iaResult&&idaResult&&catResult){
        addItemFrm.submit();
    } else{
        return;
    }
});


  
   