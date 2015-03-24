$().ready(function()
{
    $(".tab").on("click", function()
    {
        if ( ! $(this).hasClass("active"))
        {
            var target = $("#" + $(this).data("target"));

            $(this).parent().children(".tab").each(function() {
                $(this).removeClass("active");
            });

            target.parent().children(".tab-content").each(function() {
                $(this).hide();
            });

            target.fadeToggle(200);
            $(this).addClass("active");
        }
    });
});