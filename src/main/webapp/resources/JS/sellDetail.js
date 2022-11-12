const userId2 = document.getElementById("userId");
const bizId = document.getElementById("bizId");
const itemCatg2 = document.getElementById("itemCatg");

let count=0;
let idx=0;

function setCount(c){
    if(c>=0){
        count=c;
    }
}



//detail page: 파일 폴더에서 삭제=============================================================
if(bizId.value == userId2.value){
    deleteItem.addEventListener("click", function(){
        let result = window.confirm("삭제하시겠습니까?");
        if(result == false){
            return;
        } else {
            let itemNum = document.getElementById("itemNum");
            const xhttp = new XMLHttpRequest();
            xhttp.open("post","./delete");
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("itemNum="+itemNum.value);
            xhttp.onreadystatechange=function(){
                if(xhttp.readyState==4&&xhttp.status==200){
                    let result = xhttp.responseText.trim();
                        if(result>0){
                            console.log("삭제완");
                            location.href = "/sell/list?itemCatg="+itemCatg2.value;
                            } else{
                            console.log("삭제 실패");
                        }
                    }
                }
            }
        })

        };





