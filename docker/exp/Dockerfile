FROM enonic/xp-app:6.1.0-SNAPSHOT


USER root

RUN cp -r $XP_ROOT/home.org $XP_ROOT/home

# Getting Enonic XP applications from deploy folder
COPY deploy/* $XP_ROOT/home/deploy/

COPY config/* $XP_ROOT/home/config/

RUN mkdir $XP_ROOT/home/repo
RUN chown enonic-xp -R $XP_ROOT/home

USER enonic-xp