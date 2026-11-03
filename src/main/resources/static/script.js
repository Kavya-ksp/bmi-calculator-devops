
const form = document.getElementById("bmiForm");

form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const age = Number(document.getElementById("age").value);
    const weight = Number(document.getElementById("weight").value);
    const height = Number(document.getElementById("height").value);
    const weightUnit = document.getElementById("weightUnit").value;
    const heightUnit = document.getElementById("heightUnit").value;

    const resultBox = document.getElementById("result");
    const errorBox = document.getElementById("error");
    const button = document.getElementById("calculateBtn");

    resultBox.classList.add("hidden");
    errorBox.textContent = "";

    if (age <= 0 || weight <= 0 || height <= 0) {
        errorBox.textContent = "Please enter valid positive values.";
        return;
    }

    button.disabled = true;
    button.textContent = "Calculating...";

    try {
        const response = await fetch("/api/bmi/calculate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                age: age,
                weight: weight,
                weightUnit: weightUnit,
                height: height,
                heightUnit: heightUnit
            })
        });

        if (!response.ok) {
            throw new Error("API request failed. Please check your inputs.");
        }

        const data = await response.json();

        document.getElementById("bmiValue").textContent =
            "BMI: " + data.bmi;

        document.getElementById("ageGroup").textContent =
            "Age Group: " + data.ageGroup;

        resultBox.classList.remove("hidden");

    } catch (error) {
        errorBox.textContent = error.message;
    } finally {
        button.disabled = false;
        button.textContent = "Calculate BMI";
    }
});
