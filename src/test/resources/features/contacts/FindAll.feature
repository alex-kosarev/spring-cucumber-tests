# Copyright 2017 Alexander Kosarev
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# language: ru
Функция: получение списка контактов

  Сценарий: клиент запрашивает список контактов
    Дано в таблице есть контакты:
      | email                    | name         |
      | jack.daniels@example.com | Jack Daniels |
      | jim.beam@example.com     | Jim Beam     |
      | john.dewar@example.com   | John Dewar   |
    Когда клиент выполнит запрос GET /contacts
    То будет возвращён ответ со статусом 200
    Также тип содержимого будет application/json;charset=utf-8
    А ответ будет содержать 3 элемента
    А также ответ будет выведен в лог
    А также запрос будет задокументирован