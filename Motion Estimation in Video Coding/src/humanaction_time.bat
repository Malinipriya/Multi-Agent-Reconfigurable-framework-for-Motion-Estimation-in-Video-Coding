set starttime=%TIME%
set startcsec=%STARTTIME:~9,2%
set startsecs=%STARTTIME:~6,2%
set startmins=%STARTTIME:~3,2%
set starthour=%STARTTIME:~0,2%
set /a starttime=(%starthour%*60*60*100)+(%startmins%*60*100)+(%startsecs%*100)+(%startcsec%)

java -cp JADE-bin-4.3.1\jade\lib\jade.jar;classes jade.Boot -gui -agents pa1:human_action.PixelAgent1;pa2:human_action.PixelAgent2;pa3:human_action.PixelAgent3;pa4:human_action.PixelAgent4;pa5:human_action.PixelAgent5;pa6:human_action.PixelAgent6;pa7:human_action.PixelAgent7;pa8:human_action.PixelAgent8;pa9:human_action.PixelAgent9;pa10:human_action.PixelAgent10;pa11:human_action.PixelAgent11;pa12:human_action.PixelAgent12;pa13:human_action.PixelAgent13;pa14:human_action.PixelAgent14;pa15:human_action.PixelAgent15;pa16:human_action.PixelAgent16

set endtime=%time%
set endcsec=%endTIME:~9,2%
set endsecs=%endTIME:~6,2%
set endmins=%endTIME:~3,2%
set endhour=%endTIME:~0,2%
if %endhour% LSS %starthour% set /a endhour+=24
set /a endtime=(%endhour%*60*60*100)+(%endmins%*60*100)+(%endsecs%*100)+(%endcsec%)

set /a timetaken= ( %endtime% - %starttime% )
set /a timetakens= %timetaken% / 100
set timetaken=%timetakens%.%timetaken:~-2%
echo Computational Time: %timetaken% sec.