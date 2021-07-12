const form = document.getElementsByTagName("form");
const submitBtn = document.getElementById("submitBtn");
const calInput = document.getElementById("calorie");
const nameInput = document.getElementById("name");

const validateInput = (e) => {
    e.preventDefault();
    calInput.value = calInput.value.trim();
    nameInput.value = nameInput.value.trim();

    if (calInput.value == "" ||
        isNaN(calInput.value) ||
        calInput.value < 0 ||
        nameInput.value == ""
    )
    {
        alert("입력값이 잘못되었습니다.");
    }
    else
    {
        form[0].submit();
    }
}

const validateForm = () => {
    submitBtn.addEventListener("click", validateInput);
}

validateForm();