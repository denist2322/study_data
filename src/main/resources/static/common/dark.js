document.addEventListener('DOMContentLoaded', ()=>{
    document.getElementById('toggle').addEventListener("click", e=>{
        if(e.target.id == 'dark-mode'){
            document.body.classList.add("dark-mode");
        }else if(e.target.id == 'light-mode'){
            document.body.classList.remove("dark-mode");
        }
    },false);        
})