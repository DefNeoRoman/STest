# STest
<h1> Программа для сканирования каталогов </h1>
ЗАДАЧА 
<h3>1. Написать программу, которая ищет файлы в каталогах, полные пути к которым перечисляются в параметрах программы. Каталоги могут быть как сетевыми, так и нет.</h3> 
<hr>
ВЫВОД 
<h3>2. Итоговым выводом является файл в кодировке UTF-8, в котором перечислены все найденные файлы. </h3>
<hr>
<h3> 3. Допустим, итоговый файл выглядит так (формат вывода должен быть именно таким как в этом примере): </h3>
<hr>

*[file = \\epbyminsd0235\Video Materials\.DS_Store date = 2011.07.20 size = 6148]
*[file = \\epbyminsd0235\Video Materials\2008.ivc date = 2008.12.12 size = 415892]
*[file = \\epbyminsd0235\Video Materials\CDP DAM.ivc date = 2009.01.29 size = 3207246]
*[file = \\epbyminsd0235\Video Materials\.NET Mentoring Program\Acceptance Testing Through UI\2010-01-19 10.13 Acceptance Testing.wmv 
*date = 2010.01.19 size = 22904839]
*[file = \\epbyminsd0235\Video Materials\.NET Mentoring Program\Acceptance Testing Through UI\2010-01-19 10.50 Acceptance Testing.wmv 
*date = 2010.01.19 size = 106224657] 

<h3>4. Чтобы было понятно как делать нельзя, приводим разъяснения по целевому способу использования программы. </h3>

Итак, мы просканировали нашей программой все каталоги в первый раз. Далее, например, через неделю просканировали второй раз. Взяли полученные 2 файла. Сравнили их, например, с помощью WinMerge. В результате увидели: 
• что добавилось, 
• что убавилось, 
• что изменилось. 

Таким образом, если вывод от запуска к запуску будет неупорядоченным, пользы от программы не будет. 
<h3>5. Для того, чтобы не было скучно глядеть в пустую консоль, нужно сопроводить процесс сканирования выводом через каждый 6 секунд «точки» (.), а через каждую минуту палочки (|). Если вы хотите что-то «сказать» в консоль, делать это надо на английском языке. </h3>
<hr>
ВВОД 
<h3> 6. Программа должна позволять исключать каталоги из анализа. Для этого нужно использовать ключ «минус» (-) и после него перечислить полные пути каталогов, которые анализу не подлежат. </h3>
<hr>
Ниже пример параметров, при которых ничего не сканируется: 
"\\epbyminsd0235\Video Materials" "\\EPUALVISA0002.kyiv.com\Workflow\ORG\Employees\Special" "\\EPUALVISA0002.kyiv.com\Workflow\ORG\Employees\Lviv" - "\\epbyminsd0235\Video Materials" "\\EPUALVISA0002.kyiv.com\Workflow\ORG\Employees\Special" "\\EPUALVISA0002.kyiv.com\Workflow\ORG\Employees\Lviv" 
<h3>7. Должны работать разные варианты входных параметров (то есть, не должно быть такого, что программа протестирована только для одного каталога на диске C:\ c одним исключением). </h3>
<hr>
<h3>8. Должно быть учтено возможное расширение набора ключей (помимо «минуса»). Например, чтобы исключить из вывода ряд файлов (таких как Thumbs.db). </h3>
<hr>
<h2>ОБЯЗАТЕЛЬНЫЕ ТРЕБОВАНИЯ </h2>
<h3>9. Функциональность программы должна быть покрыта JUnit-тестами. Должна быть обеспечена возможность запуска всех тестов сразу. </h3>
<hr>
<h3>10. Для ускорения работы программы разработчик должен использовать многопоточность. На умение работать с потоками будет обращено особое внимание. </h3>
<hr>
<h3>11. Файлов во всех каталогах может быть несколько миллионов. </h3>
<hr>
<h3>12. Программа должна работать быстро и с экономным потреблением оперативной памяти. </h3>
<hr>
<h3>13. Любой выбор структур данных, подходов и алгоритмов должен быть лаконично и емко обоснован комментарием. Например, если был сделан выбор в пользу ArrayList (150000), нужно пояснение почему. Если размер файлового буфера выбран равным 8192, то почему и т.д. Из кода и комментариев в нем (на русском языке) должен быть ясен ход мысли автора. Мы должны видеть, что выбор сделан с пониманием, а не случайно. </h3>
<hr>
<h3>14. Ход программы, ее алгоритм, ее циклы должны быть простыми и без замысловатостей, чтобы не приходилось тратить нервные клетки мозга на понимание. </h3>
<hr>
<h3>15. Должна использоваться Java версии 6 или больше. </h3>
<hr>
<h3>16. Использовать сторонние программы и библиотеки нельзя. Только Java SE. Это, конечно, не касается JUnit- тестов </h3>
<hr>
<h3>17. Все другие вопросы по особенностям реализации приложения решаются разработчиком самостоятельно, эти решения отражают знания и опыт разработчика и учитываются при оценке созданного продукта. </h3>
<hr>
<h3>18. Приложение должно решать поставленные задачи, выполнение дополнительных функций, не приведенных в задании, не влияет на оценку решения (за редким исключением). </h3>
<hr>
<h3>19. При оценке решения учитываются следующие факторы: 
• применение принципов ООП, возможность расширения приложения и повторного использования кода, 
• простота и читаемость кода (в том числе комментирование), 
• архитектура решения (способы работы с данными), 
• структура кода. </h3>
<hr>
<h3>20. Готовое решение должно представлять из себя архив, в котором находятся исходные тексты программы и JUnit-тестов. </h3>
