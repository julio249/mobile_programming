document.addEventListener("DOMContentLoaded", function() {
});

var windowEl = $(window);
var header = document.querySelector("header");
function toggleStickyHeader() {
    var dist = windowEl.scrollTop();

    if (dist >= 100 && !header.classList.contains("hidden")) {
        //header.classList.add("hidden");
        setTimeout(function() {
            header.classList.add("sticky");
        }, 250);
    } else if (dist <= (100 + 100) && header.classList.contains("hidden")) {
        header.classList.remove("sticky");
        setTimeout(function() {
            header.classList.remove("hidden");
        }, 150);
    }
}
$(window).on("scroll", function() {
    toggleStickyHeader();
});
document.querySelector(".menu").addEventListener("click", function(e) {
    toggleVisibleClass(e, ".slide-out-container");
});
document.querySelector(".nav-close").addEventListener("click", function(e) {
    toggleVisibleClass(e, ".slide-out-container");
});
function toggleVisibleClass(e, elClass) {
    e.preventDefault();
    var toggleEl = document.querySelector(elClass);
    toggleEl.classList.toggle("visible");
}

/** NEWSLETTER LABEL ANIMATION **/
function formLabelAnimation(el) {
    if (el.value === "") {
        el.previousElementSibling.classList.remove("filled");
    }
}
/** NEWSLETTER EMAIL VALIDATION **/
function isValidNewsEmail(emailAddress) {
    var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
    return pattern.test(emailAddress);
}
/** DISPLAY ERROR IF FALSE, SUBMIT FORM IF TRUE **/
function sendNewsletterForm(emailAddress, e) {
    e.preventDefault();
    var $newsletterError = $(".newsletter-error");

    if (isValidNewsEmail(emailAddress)) {
        var formData = { EmailAddress: emailAddress, ClientId: 1 };

        $newsletterError.text("Sending...");
        $newsletterError.addClass("visible");

        // AJAX TO SEND NEWSLETTER FORM DATA //
        $.ajax({
            method: "POST",
            url: 'https://partners.stubs.net/api/V1/patrons/NewsletterSignUp',
            dataType: "json",
            data: JSON.stringify(formData),
            contentType: "application/json",
            timeout: 5000,
            success: function() {
                $("form.newsletter .user-form-field").remove();
                $newsletterError.text("Thank you for joining!");
            },
            error: function() {
                $newsletterError.text("There was an error sending the form. Please try again in a moment.");
            },
        });
    } else {
        $newsletterError.addClass("visible");
    }
}
// NEWSLETTER FORM LABEL ANIMATION
if (_msf(".newsletter").length > 0) {
    _msf("#newsletter-email").on("focus", function (e) {
        _msf(this).addClass("filled");
    });
    _msf("#newsletter-email").on("blur", function (e) {
        if (this.value == "") {
            _msf(this).removeClass("filled");
        }
    });
}
// VALIDATE/SUBMIT NEWSLETTER FORM
$("form.newsletter").on("submit", function (e) {
    var emailAddress = $("#newsletter-email").val();
    sendNewsletterForm(emailAddress, e);
});

//INSTAGRAM FEED
if (document.querySelector("#instafeed")) {
    initInstagramFeed(9, "#instafeed");
}
function initInstagramFeed(numOfItems, feedContainerSelector) {
    var xhr = new XMLHttpRequest();
    function displayFeedItems(numOfItems, feedContainerSelector, response) {
        var allItems = JSON.parse(response);
        allItems = allItems.data;
        for (let i = 0; i < numOfItems; i++) {
            let feedItem = document.createElement("a");
            let bkgImgSrc = (allItems[i].thumbnail_url != undefined) ? allItems[i].thumbnail_url : allItems[i].media_url;
            let overlay = '<div class="overlay ol-green"><div class="txt-on-overlay grid f-jc-center f-ai-center"><img src="' + msf_config.siteRoot + '/site/images/ig-icon.svg" /><p class="txt-upper txt-md col-100 m-0">VIEW ON INSTAGRAM</p></div></div>';
            feedItem.classList = "gal-item bkg-img-center";
            feedItem.setAttribute("target", "_blank");
            feedItem.setAttribute("href", allItems[i].permalink);
            feedItem.setAttribute("style", "background-image:url(" + bkgImgSrc + ")");
            feedItem.innerHTML = overlay;
            _msf(feedContainerSelector)[0].append(feedItem);
        }
    }
    xhr.addEventListener("load", function() {
        var response = this.response;
        displayFeedItems(numOfItems, feedContainerSelector, response);
    });
    xhr.open("GET", "https://graph.instagram.com/me/media?fields=id,media_url,permalink,thumbnail_url&access_token=IGQWRQV0luVXdTWW43U1Y0UEM3SmZACOTc2UlVnMXgwbkFYRGJNc3AtbXJPWmlHZAWJ6NHlqcFNkQm1uc205ZA3kyRnZATZADY4UUYyX1FQaFRRdGFXZAFpWR3RYSzNDLWtUa2ZAqQnhDa05sbnJpYjZA2enJDRW9pSXBueEEZD");
    xhr.send();
}

// SHOWS ROTATOR
if (document.querySelector(".swiper-container-shows")) {
    initShowsSwiper();
}
/*** SHOWS ROTATOR ***/
function initShowsSwiper() {
    var showSwiper = new Swiper('.swiper-container-shows', {
        speed: 400,
        spaceBetween: 30,
        slidesPerView: 'auto',
        navigation: {
            nextEl: ".home-shows-swiper.swiper-button-next",
            prevEl: ".home-shows-swiper.swiper-button-prev"
        },
        breakpoints: {
            //when window width is <= 768px
            768: {
                centeredSlides: true,
            },
        }
    });
}