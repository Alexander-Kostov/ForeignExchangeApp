function toggleFilterForm() {
        var filterOption = document.getElementById("filterOption").value;
        var dateForm = document.getElementById("date");
        var idForm = document.getElementById("ID");

        if (filterOption === "date") {
            dateForm.style.display = "block";
            idForm.style.display = "none";
        } else {
            dateForm.style.display = "none";
            idForm.style.display = "block";
        }
}

function validateForm() {
        var filterOption = document.getElementById("filterOption").value;

        if (filterOption === "date") {
            var fromDate = document.getElementById("fromDate").value;
            var toDate = document.getElementById("toDate").value;
            if (fromDate === "" || toDate === "") {
                alert("Please select both 'From Date' and 'To Date'.");
                return false;
            }

            if (fromDate > toDate) {
                alert("'From Date' cannot be later than 'To Date'.");
                return false;
            }
            document.getElementById("fromId").value = "";
            document.getElementById("toId").value = "";
        } else if (filterOption === "id") {
            var fromId = parseInt(document.getElementById("fromId").value);
            var toId = parseInt(document.getElementById("toId").value);
            if (fromId === "" || toId === "") {
                alert("Please fill in both 'From ID' and 'To ID'.");
                return false;
            }

            if (fromId >= toId) {
                alert("'From ID' must be smaller than 'To ID'.");
                return false;
            }

            if (fromId == 0) {
                alert("The records in the database start from 1");
                return false;
            }

            if (fromId < 0) {
                alert("'From ID' cannot be negative value.");
                return false;
            }

            if (toId < 0) {
                alert("'To ID' cannot be negative value.");
                return false;
            }


            document.getElementById("fromDate").value = "";
            document.getElementById("toDate").value = "";
        }
        return true;
}