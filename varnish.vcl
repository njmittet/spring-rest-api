vcl 4.0;

backend default {
    .host = "backend";
    .port = "9000";
}

sub vcl_recv {
    return(pass);
}

sub vcl_backend_fetch {
    set bereq.http.Authorization = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";
}
