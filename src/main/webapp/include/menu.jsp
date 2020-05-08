<aside>
    <center>
        <a href="/remytutor"><img src="images/logo_transparent.png" alt=""></a>
    </center>

    <p><label for="toggle_menu"><i class="fas fa-bars"></i></label></p>
    <a href="/remytutor/dashboard"${currentpage == '/dashboard' ? ' class="current_section"' : ''}><i class="fas fa-desktop"></i><span>Control Panel</span></a>
    <a href="/remytutor/chat"${currentpage == '/chat' ? ' class="current_section"' : ''}><i class="fas fa-comment-dots"></i><span>Chat</span></a>
    <a href="/remytutor/feedbacks"${currentpage == '/feedbacks' ? ' class="current_section"' : ''}><i class="far fa-thumbs-up"></i><span>Feedbacks</span></a>
    <a href="/remytutor/payments"${currentpage == '/payments' ? ' class="current_section"' : ''}><i class="fas fa-money-check-alt"></i><span>Payments</span></a>
    <a href="/remytutor/profile"${currentpage == '/profile' ? ' class="current_section"' : ''}><i class="fas fa-user-alt"></i><span>Profile</span></a>
    <a href="/remytutor/dashboard?signout=true"><i class="fas fa-info-circle"></i><span>Sign out</span></a>

</aside>