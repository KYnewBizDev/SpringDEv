// 페이지 이동
function movePage(page) {
  let urlParams = new URLSearchParams(window.location.search);
  urlParams.delete("page");
  urlParams.append("page", page);
  console.log(urlParams.toString());
  location.href =  window.location.pathname +"?"+ urlParams.toString();
}
// 페이지
/*
function Pagination(page, totalPages){
  if(page>0 && totalPages>0){
    var html = '';
    pageSize = 10; // 노출 페이지수
    start = parseInt( parseInt( parseInt(page - 1 ) / pageSize ) * pageSize ) + 1;

    end = start + pageSize - 1;
    if (end >= totalPages) end = totalPages;

    prev = (start>1)?start-1:start;
    next = (totalPages > end)?end+1:end;

    html += '<ul class="paging">';
    html += '  <li><button class="btn_arrow first" onclick="movePage(1)"></button></li>'; //if(page > 1)
    html += '  <li><button class="btn_arrow prev" onclick="movePage('+prev+')"></button></li>'; //if(start > 1)

    // if (totalPages > 1) {
    html += '  <li class="num">';
      for (var k = start; k <= end; k++) {
        if(page == k){
          html += '  <a href="javascript:void(0);" class="on">'+k+'</a>';
        }else{
          html += '  <a href="javascript:void(0);" onclick="movePage('+k+');">'+k+'</a>';
        }
      }
    html += '  </li>';
    // }

    html += '  <li><button class="btn_arrow next" onclick="movePage('+next+')"></button></li>'; //if(totalPages > end)
    html += '  <li><button class="btn_arrow last" onclick="movePage('+totalPages+')"></button></li>'; //if(page < totalPages)
    html += '</ul>';

    // document.querySelector('.pagination').innerHTML = html;
    var sections = document.querySelectorAll('.pagination');
    for (var i = 0; i < sections.length; i++) {
      sections.item(i).innerHTML = html;
    }

  }
}
*/

// 에디터 (썸머노트)
function Editor(target){
  $(target).summernote({
     height: 300
     , lang: "ko-KR"
     , toolbar: [
       ['style', ['style']],
       ['font', ['bold', 'underline', 'clear']],
       ['fontname', ['fontname']],
       ['color', ['color']],
       ['para', ['ul', 'ol', 'paragraph']],
       ['table', ['table']],
       ['insert', ['link', 'picture', 'video']],
       ['view', ['codeview', 'help']]
     ]
     ,callbacks: {
        onImageUpload : function(files) {
          for (var i = files.length - 1; i >= 0; i--) {
            EditorUploadImage(files[i], this);
          }
        },
        onPaste: function (e) {
          var clipboardData = e.originalEvent.clipboardData;
          if (clipboardData && clipboardData.items && clipboardData.items.length) {
            var item = clipboardData.items[0];
            if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
              e.preventDefault();
            }
          }
        }
     }
  });
}
// 에디터 (썸머노트) - 이미지업로드
function EditorUploadImage(file, editor) {
  data = new FormData();
  data.append("file", file);
  $.ajax({
    data : data,
    type : "POST",
    url : "/common/EditorUploadImage",
    contentType : false,
    processData : false,
    success : function(data) {
      $(editor).summernote('insertImage', data.url);
    }
  });
}