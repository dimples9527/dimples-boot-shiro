layui.define(['jquery'], function (exports) {
    let $ = layui.jquery;
    let CLS_DROPDOWN = 'layui-dropdown';
    let CLS_DROPDOWN_RIGHT = 'layui-dropdown-direright';
    let CLS_SELECT = 'layui-dropdown-select';
    let CLS_OPTION = 'layui-dropdown-option';
    let CLS_TITLE = 'layui-dropdown-title';
    let CLS_ARROW = 'dimples-arrow-up';
    let HTML_DROPDOWN = '<div class="' + CLS_DROPDOWN + '"><div>';
    let DEPTH = 0;
    let INDEX = 0;

    let Class = function (config) {
        this.config = $.extend({}, this.config, config);
        this.render(config)
    };
    Class.prototype.config = {
        width: 150,
        trigger: 'click'
    };
    Class.prototype.dropdownElem = '';
    Class.prototype.exists = false;
    Class.prototype.depth = 0;
    Class.prototype.index = 0;
    Class.prototype.render = function (config) {
        let self = this;
        if (typeof this.config.elem == 'string') {
            $(document).on('click', this.config.elem, event)
        } else {
            this.config.elem.click(event)
        }

        function event(e) {
            let body = $('body');
            e.stopPropagation();
            if (self.dropdownElem === '') {
                INDEX += 1;
                self.index = INDEX;

                let dropdown = $(HTML_DROPDOWN).attr('lay-index', self.index);
                $('.' + CLS_DROPDOWN + '[lay-index="' + self.index + '"]').remove();

                dropdown.html(self.createOptionsHtml(config));
                body.prepend(dropdown);
                dropdown.on('click', '.' + CLS_OPTION, function (e) {
                    e.stopPropagation();
                    if ($.isFunction(config.click)) {
                        config.click($(this).attr('lay-name'), $(this), e);
                        dropdown.hide()
                    }
                });
                self.dropdownElem = dropdown;
                self.dropdownSelect = dropdown.find('.' + CLS_SELECT)
            }

            let dropdown = self.dropdownElem;

            let top = $(this).offset().top + $(this).height() + 12;
            let left = $(this).offset().left - 5;
            dropdown.css({
                top: top - 10
            });
            let offsetWidth = (self.depth + 1) * self.config.width;

            if (left + offsetWidth > $(window).width()) {
                dropdown
                    .addClass('layui-dropdown-right')
                    .css('left', left - dropdown.width() + $(this).width());
                self.dropdownSelect.css({left: 'auto', right: self.config.width})
            } else {
                dropdown.removeClass('layui-dropdown-right').css('left', left);
                self.dropdownSelect.css({right: 'auto', left: self.config.width})
            }

            body.one('click', function (e) {
                dropdown.stop().animate(
                    {
                        top: '-=100000',
                        opacity: 0
                    },
                    1
                );
            });

            dropdown
                .show()
                .stop()
                .animate(
                    {
                        top: '+=10',
                        opacity: 1
                    },
                    250
                )
        }
    };
    Class.prototype.createOptionsHtml = function (data, depth) {
        depth = depth || 0;
        let self = this;
        let width = self.config.width + 'px;';
        let html =
            '<div class="' +
            CLS_SELECT +
            '" style="width:' +
            width +
            (depth > 0 ? 'left:' + width : '') +
            '">';
        if (depth === 0) {
            html += '<div class="' + CLS_ARROW + '"></div>'
        }
        layui.each(data.options, function (i, option) {
            // TODO : 暂时没有权限控制，所以全部放开
            let paserHtml = false;
            // let permissions = currentUser.permissionSet;
            let options = option.options || [];
            /*if (option.perms) {
                if (permissions.indexOf(option.perms) !== -1) {
                    paserHtml = true;
                }
            } else {
                paserHtml = true;
            }*/
            paserHtml = true;
            if (paserHtml) {
                html +=
                    '<div lay-name=' +
                    option.name +
                    ' class="' +
                    CLS_OPTION +
                    '"><p class="' +
                    CLS_TITLE +
                    ' layui-elip"><span class="layui-icon ' +
                    option.icon +
                    '"></span>' +
                    option.title +
                    '</p>' +
                    (options.length > 0
                        ? '<i class="layui-icon layui-icon-right"></i>'
                        : '');
                option.options = option.options || [];
                if (option.options.length > 0)
                    html += self.createOptionsHtml(option, depth + 1);
                html += '</div>';
                if (self.depth < depth) self.depth = depth
            }
        });
        html += '</div>';
        return html
    };

    let self = {
        render: function (config) {
            new Class(config)
        }
    };
    exports('dropdown', self)
});
