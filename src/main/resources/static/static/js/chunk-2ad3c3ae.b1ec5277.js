(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2ad3c3ae"],{"299b":function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-card",{staticStyle:{height:"100%"}},[n("div",{attrs:{slot:"header"},slot:"header"},[n("span",[t._v("Hoàn thành các bước để đăng ký vườn")])]),n("el-card",{staticStyle:{"padding-bottom":"50px"}},[n("el-steps",{staticStyle:{"margin-bottom":"50px"},attrs:{active:t.currentActiveStep,"align-center":""}},[n("el-step",{attrs:{title:"Bước 1"}},[n("template",{slot:"description"},[0===t.currentActiveStep?n("span",[t._v(" Hoàn thành các thông tin bản thân ")]):t.currentActiveStep>0?n("i",{staticClass:"el-icon-check"}):t._e()])],2),n("el-step",{attrs:{title:"Bước 2"}},[n("template",{slot:"description"},[1===t.currentActiveStep?n("span",[t._v(" Hoàn thành các thông tin Vườn ")]):t.currentActiveStep>1?n("i",{staticClass:"el-icon-check"}):t._e()])],2),n("el-step",{attrs:{title:"Bước 3"}},[n("template",{slot:"description"},[2===t.currentActiveStep?n("span",[t._v(" Hoàn thành ")]):t.currentActiveStep>2?n("i",{staticClass:"el-icon-check"}):t._e()])],2)],1),n("div",[n("div",{directives:[{name:"show",rawName:"v-show",value:0===t.currentActiveStep,expression:"currentActiveStep===0"}]},[n("h4",[t._v(" Xem xét việc chỉnh sửa thông tin cá nhân của bạn cho chính xác nhất để việc xét duyệt trở nên dễ dàng hơn ")]),n("el-collapse",{model:{value:t.activeNames,callback:function(e){t.activeNames=e},expression:"activeNames"}},[n("el-collapse-item",{attrs:{title:"Quản lý",name:"1"}},[n("div",[t._v("Khi bạn tham gia với chúng tôi, chúng tôi sẽ cung cấp cho bạn khả năng quản lý vườn của mình, cập nhật, theo dõi và đưa dữ liệu của vườn bạn hiển thị một cách tốt nhất ")]),n("div",[t._v("Quản lý về thông tin vườn, mô tả, trồng mới, cập nhật số lượng cây trong vườn")])]),n("el-collapse-item",{attrs:{title:"Hỗ trợ",name:"2"}},[n("div",[t._v("Với khu vườn của bạn một khi đăng ký thành công, vườn sẽ được hỗ trợ kỹ thuật từ một chuyên gia cây trồng ")]),n("div",[t._v("Người chuyên gia cây trồng phụ trách hỗ trợ kỹ thuật cho vườn của bạn sẽ có nhiệm vụ giúp đỡ, tư vấn, hỗ trợ bạn khi bạn có nhu cầu cần hỗ trợ ")])]),n("el-collapse-item",{attrs:{title:"Phát triển du lịch",name:"3"}},[n("div",[t._v("Khi bạn đã có vườn, bạn có thể tạo các bài quảng cáo du lịch, tạo thêm các giá trị mới và giúp khách du lịch có được trải nghiệp là các hoạt động chăm sóc, trồng mới, thu hoạch sản phẩm..... ")]),n("div",[t._v("Khi du khách tham gia các hoạt động, bạn cần phải thêm các du khách đó vào dữ liệu du lịch vườn, từ đó các du khách có thể thấy đóng góp của mình và sẽ có xu hướng quay trở về để tham gia tiếp ")]),n("div",[t._v("Mỗi đợt như vậy, bạn có thể xem thống kê về du lịch và hoạt động của vườn bạn, bạn sẽ cảm thấy có thêm nhiều động lực hơn đó ")])])],1)],1),n("el-form",{directives:[{name:"show",rawName:"v-show",value:1===t.currentActiveStep,expression:"currentActiveStep === 1"}],ref:"farmForm",attrs:{model:t.farmForm,rules:t.farmFormRules}},[n("el-card",{staticClass:"el-card"},[n("span",{attrs:{slot:"header"},slot:"header"},[t._v("Tên vườn đăng ký")]),n("el-form-item",{attrs:{prop:"name"}},[n("el-input",{attrs:{placeholder:"Tên vườn"},model:{value:t.farmForm.name,callback:function(e){t.$set(t.farmForm,"name",e)},expression:"farmForm.name"}})],1)],1),n("el-card",{staticClass:"el-card"},[n("span",{attrs:{slot:"header"},slot:"header"},[t._v("Địa chỉ")]),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{prop:"province",label:"Cấp tỉnh",tabindex:"2"}},[n("el-autocomplete",{ref:"inputProvince",staticClass:"el-autocomplete",attrs:{placeholder:"Cấp tỉnh","value-key":"name","fetch-suggestions":t.fetchProvinceSuggestions},on:{select:t.handleSelectProvince},model:{value:t.farmForm.province.name,callback:function(e){t.$set(t.farmForm.province,"name",e)},expression:"farmForm.province.name"}})],1)],1),n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{prop:"district",label:"Cấp huyện",tabindex:"3"}},[n("el-autocomplete",{ref:"inputFarmDistrict",staticClass:"el-autocomplete",attrs:{"value-key":"name",placeholder:"Cấp huyện","fetch-suggestions":t.fetchDistrictSuggestions},on:{focus:t.focusDistrict,select:t.handleSelectDistrict},model:{value:t.farmForm.district.name,callback:function(e){t.$set(t.farmForm.district,"name",e)},expression:"farmForm.district.name"}})],1)],1),n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{prop:"ward",label:"Cấp xã",tabindex:"4"}},[n("el-autocomplete",{ref:"inputFarmWard",staticClass:"el-autocomplete",attrs:{"value-key":"name",placeholder:"Cấp xã","fetch-suggestions":t.fetchWardSuggestions},on:{select:t.handleSelectWard,focus:t.focusWard},model:{value:t.farmForm.ward.name,callback:function(e){t.$set(t.farmForm.ward,"name",e)},expression:"farmForm.ward.name"}})],1)],1)],1),n("el-row",[n("el-form-item",{attrs:{prop:"detailAddress",label:"Vị trí cụ thể",tabindex:"5"}},[n("el-input",{attrs:{placeholder:"Vị trí cụ thể"},model:{value:t.farmForm.detailAddress,callback:function(e){t.$set(t.farmForm,"detailAddress",e)},expression:"farmForm.detailAddress"}})],1)],1)],1)],1),n("div",{directives:[{name:"show",rawName:"v-show",value:2===t.currentActiveStep,expression:"currentActiveStep === 2"}],staticStyle:{"text-align":"center","margin-top":"-30px"}},[n("el-result",{attrs:{icon:"success",title:"Đăng ký thành công","sub-title":"Nếu bạn cần một người hướng dẫn, một mentor để hỗ trợ bạn phát triển khu vườn, hãy đăng ký trong mục quản lý"}},[n("template",{slot:"extra"},[n("el-button",{attrs:{type:"primary",size:"medium",icon:"el-icon-right"},on:{click:t.handleRouteToFarm}},[t._v("Đi tới khu vực quản lý ")])],1)],2)],1)],1),n("el-button",{directives:[{name:"show",rawName:"v-show",value:2!==t.currentActiveStep,expression:"currentActiveStep !== 2"}],staticStyle:{"margin-top":"20px",float:"right"},attrs:{type:"primary",loading:t.buttonNextStepLoading},on:{click:t.handleNextStep}},[t._v("Bước tiếp "),n("i",{staticClass:"el-icon-arrow-right el-icon-right"})])],1)],1)},i=[],c=n("5530"),a=(n("d3b7"),n("caad"),n("2532"),n("498a"),n("b0c0"),n("d415")),o=n("2f94"),s=n("2f62"),l=(n("a18c"),{name:"Index",data:function(){return{currentActiveStep:1,activeNames:[],farmForm:{name:"",detailAddress:"",province:{name:""},district:{name:""},ward:{name:""},descriptions:[{label:"",value:""}]},farmFormRules:{name:[{required:!0,validator:this.validateName}],province:[{validator:this.checkProvince}],district:[{validator:this.checkDistrict}],ward:[{validator:this.checkWard}]},buttonNextStepLoading:!1}},computed:Object(c["a"])({},Object(s["b"])(["roles"])),methods:{handleNextStep:function(){var t=this;this.currentActiveStep<2?1===this.currentActiveStep?this.$refs["farmForm"].validate((function(e){e?(t.buttonNextStepLoading=!0,t.handleCreateFarm().then((function(e){t.currentActiveStep++,console.log("created farm, ",e),t.roles.includes("farmer")||t.$store.dispatch("user/changeRoles")})).catch((function(e){t.$message.error("Đăng ký vườn không thành công, vui lòng thử lại sau"),console.log(e)})).finally((function(){t.buttonNextStepLoading=!1}))):t.$message.warning("Vui lòng kiểm tra lại thông tin")})):this.currentActiveStep++:this.currentActiveStep=0},validateName:function(t,e,n){null==e||e.trim()<8?n(new Error("Tên vườn không hợp lệ")):n()},validateAddress:function(t,e,n){null==e||e.trim()<3?n(new Error("Địa chỉ không hợp lệ")):n()},checkProvince:function(t,e,n){void 0===e.id?n(new Error("Tỉnh là thông tin bắt buộc")):n()},checkDistrict:function(t,e,n){void 0===e.id?n(new Error("Huyện là thông tin bắt buộc")):n()},checkWard:function(t,e,n){void 0===e.id?n(new Error("Xã là thông tin bắt buộc")):n()},handleCreateFarm:function(){var t=this.farmForm.descriptions.reduce((function(t,e){return e.label.trim().length>0&&e.value.trim().length>0&&(t[e.label]=e.value),t}),{}),e={name:this.farmForm.name,province:{id:this.farmForm.province.id},district:{id:this.farmForm.district.id},ward:{id:this.farmForm.ward.id},detailAddress:this.farmForm.detailAddress,descriptions:t};return console.log("send farm form creation, ",e),new Promise((function(t,n){Object(a["a"])(e).then((function(e){return t(e)})).catch((function(t){return n(t)}))}))},handleRouteToFarm:function(){this.$router.push("/farm")},fetchProvinceSuggestions:function(t,e){Object(o["b"])(t).then((function(t){e(t)}))},fetchDistrictSuggestions:function(t,e){var n=this.farmForm.province.id;void 0!==n&&Object(o["a"])(n,t).then((function(t){e(t)}))},fetchWardSuggestions:function(t,e){var n=this.farmForm.district.id;void 0!==n&&Object(o["c"])(n,t).then((function(t){e(t)}))},focusDistrict:function(){void 0===this.farmForm.province.id&&(this.$message.warning("Vui lòng chọn Tỉnh trước"),this.$refs["inputFarmDistrict"].$refs.input.blur())},focusWard:function(){void 0===this.farmForm.province.id?(this.$message.warning("Vui lòng chọn Tỉnh"),this.$refs["inputFarmWard"].$refs.input.blur()):void 0===this.farmForm.district.id&&(this.$message.warning("Vui lòng chọn Huyện"),this.$refs["inputFarmWard"].$refs.input.blur())},handleSelectProvince:function(t){this.farmForm.province=t,console.log("select province, ",t),this.farmForm.district={name:""},this.farmForm.ward={name:""}},handleSelectDistrict:function(t){this.farmForm.district=t,console.log("select district, ",t),this.farmForm.ward={name:""}},handleSelectWard:function(t){this.farmForm.ward=t,console.log("select ward, ",t)}}}),h=l,u=(n("e7b8"),n("2877")),m=Object(u["a"])(h,r,i,!1,null,"4c12d3c9",null);e["default"]=m.exports},"2f94":function(t,e,n){"use strict";n.d(e,"b",(function(){return i})),n.d(e,"a",(function(){return c})),n.d(e,"c",(function(){return a}));var r=n("b775");function i(t){return void 0!==t&&null!=t||(t=""),Object(r["a"])({url:"/address/province",method:"get",params:{name:t}})}function c(t,e){return console.log("provinceId, ",t,"name, ",e),Object(r["a"])({url:"/address/district",method:"get",params:{provinceId:t,name:e}})}function a(t,e){return Object(r["a"])({url:"/address/ward",method:"get",params:{districtId:t,name:e}})}},bedc:function(t,e,n){},d415:function(t,e,n){"use strict";n.d(e,"b",(function(){return i})),n.d(e,"a",(function(){return c})),n.d(e,"e",(function(){return a})),n.d(e,"f",(function(){return o})),n.d(e,"d",(function(){return s})),n.d(e,"c",(function(){return l}));var r=n("b775");n("0b16");function i(){return Object(r["a"])({url:"/farm/all",method:"get"})}function c(t){return Object(r["a"])({url:"/farm",method:"post",data:t})}function a(t){return Object(r["a"])({url:"/farm",method:"put",data:t})}function o(t){return Object(r["a"])({url:"/farm/tree",method:"put",data:t})}function s(t){return Object(r["a"])({url:"/tree/search",method:"get",params:{name:t}})}function l(t){return console.log("insert tree"),Object(r["a"])({url:"/tree",method:"post",data:t})}},e7b8:function(t,e,n){"use strict";n("bedc")}}]);