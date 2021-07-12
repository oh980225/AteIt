const deleteBtn = document.getElementsByClassName("deleteBtn");

const deleteDiet = () => {
    for (const btn of deleteBtn) {
        btn.addEventListener("click", () => {
            const deleteConfirm = confirm("삭제하시겠습니까?");
            if(deleteConfirm == true) {
                const id =  btn.parentNode.id
                window.location.href = '/diet/delete/' + id;
                console.log("DELETE");
            } else {
                console.log("NOT DELETE");
            }
        });
    }
}

deleteDiet();