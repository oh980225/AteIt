const deleteBtn = document.getElementsByClassName("deleteBtn");

function deleteDiet(id) {
    const url = "http://localhost:8080/diet/delete/" + id;
    $.ajax({
        url: url,
        type: "DELETE",
        success: () => {
            window.location.reload();
        }
    });
}

for (const btn of deleteBtn) {
    btn.addEventListener("click", () => {
        const deleteConfirm = confirm("삭제하시겠습니까?");
        if(deleteConfirm == true) {
            const id =  btn.parentNode.id
            deleteDiet(id);
            console.log("삭제한다고 합니다~");
        } else {
            console.log("삭제 취소");
        }
    });
}
