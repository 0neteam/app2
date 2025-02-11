document.addEventListener("DOMContentLoaded", function () {
    const textElement = document.querySelector(".animated-text");
    const text = textElement.textContent;
    textElement.textContent = ""; // 기존 텍스트 지우기

    function animateText() {
        text.split("").forEach((char, index) => {
            const span = document.createElement("span");
            span.textContent = char === " " ? "\u00A0" : char; // 공백을 &nbsp;로 변환
            span.classList.add("letter");
            span.style.transitionDelay = `${index * 0.1}s`; // 각 글자마다 0.1초 간격 적용
            textElement.appendChild(span);

            setTimeout(() => {
                span.style.opacity = "1";
                span.style.transform = "translateX(0)";
            }, 50);
        });
    }

    function resetText() {
        const letters = document.querySelectorAll(".letter");
        letters.forEach(letter => {
            letter.style.opacity = "0";
            letter.style.transform = "translateX(-20px)";
        });
        setTimeout(() => {
            textElement.textContent = ""; // 텍스트 리셋
            animateText(); // 애니메이션 다시 시작
        }, 500); // 애니메이션 초기화 후 다시 시작
    }

    animateText(); // 애니메이션 처음 실행

    // 5초마다 애니메이션 반복
    setInterval(resetText, 10000); 
});