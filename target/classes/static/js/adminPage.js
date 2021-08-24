
  function checkThis() {
      const inputName = document.getElementsByName('name');
      if(inputName.val() == null){
          alert("分类名称不能为空");
      }


  }
  function checkForm(form) {
      let els = form.getElementsByTagName('input');
      for(let i=0; i<els.length; i++){
          if(els[i] != null){
              if(els[i].getAttribute("onmouseleave")){
                  check(els[i]);
              }
          }
      }
      return flag;
  }

 