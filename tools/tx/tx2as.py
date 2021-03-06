#!/usr/bin/env python3
import json
import os
import pprint
import re
from lxml import etree as ElementTree

dstDir = "../../RemixedDungeon/src/main/res/"

xml_ext = '.xml'
translations_dir = 'translations/'

source_locales = {"en","tr","ko","hu","it",'de_DE', 'es_ES', 'fr_FR', 'pl_PL', 'ru_RU',
                  'uk_UA', 'pt_BR', "ms_MY","zh"}

locale_remap = {'de_DE': 'de', 'es_ES': 'es', 'fr_FR': 'fr', 'pl_PL': 'pl', 'nl_NL': 'nl', 'ro_RO': 'ro', 'ru_RU': 'ru',
                'uk_UA': 'uk', 'pt_BR': 'pt-rBR', 'pt_PT': 'pt-rPT', 'es_MX': 'es-rMX', "ms_MY": "ms"}

counters = {}
totalCounter = {}

dir_name = "remixed-dungeon.strings-all-xml--master"
resource_name = "strings_all.xml"

escape_apostrophe = re.compile(r"(?<!\\)'")

print("Processing:", dir_name, resource_name)

counters[resource_name] = {}


def indent(elem, level=0):
    i = "\n" + level*"  "
    if len(elem):
        if not elem.text or not elem.text.strip():
            elem.text = i + "  "
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
        for elem in elem:
            indent(elem, level+1)
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
    else:
        if level and (not elem.tail or not elem.tail.strip()):
            elem.tail = i


def unescape(arg):
    return arg.replace("\\\\", "\\").replace("\\\\", "\\")


def processText(arg):
    if arg is None:
        return arg

    ret = unescape(arg)
    ret = escape_apostrophe.sub(r"\'", ret)
    return ret


for _, _, files in os.walk(translations_dir + dir_name):

    arrays = ElementTree.Element("resources")

    for file_name in files:

        locale_code = file_name[:-4]

        if not locale_code in source_locales:
            continue

        if locale_code in locale_remap:
            locale_code = locale_remap[locale_code]


        if locale_code not in totalCounter:
            totalCounter[locale_code] = 0

        counters[resource_name][locale_code] = 0

        if locale_code == 'en':
            resource_dir = dstDir + "values"
        else:
            resource_dir = dstDir + "values-" + locale_code

        if not os.path.isdir(resource_dir):
            os.makedirs(resource_dir)

        currentFilePath = translations_dir + dir_name + "/" + file_name

        print("file:", currentFilePath)
        try:
            transifexData = ElementTree.parse(currentFilePath).getroot()

            jsonData = open("strings_" + locale_code + ".json", "w", encoding='utf8')

            for entry in transifexData:

                if entry.tag not in ["string", "string-array"]:
                    continue

                counters[resource_name][locale_code] += 1
                totalCounter[locale_code] += 1

                if entry.tag == "string":
                    jsonData.write(unescape(json.dumps([entry.get("name"), entry.text], ensure_ascii=False)))
                    jsonData.write("\n")

                # if entry.tag == "string-array":
                #     arrayDesc = [entry.get("name")]
                #     arrayIndex = 0
                #     for arrayItem in entry:
                #         arrayItemText = processText(arrayItem.text)
                #         arrayDesc.append(arrayItemText)
                #
                #         newEntry = ElementTree.Element("string")
                #
                #         stringItemName = entry.get("name") + "_" + str(arrayIndex)
                #
                #         newEntry.set("name", stringItemName)
                #         newEntry.text = arrayItemText
                #         transifexData.append(newEntry)
                #
                #         arrayIndex = arrayIndex + 1
                #         arrayItem.text = "@string/"+stringItemName
                #
                #     jsonData.write(unescape(json.dumps(arrayDesc, ensure_ascii=False)))
                #     jsonData.write("\n")
                #     transifexData.remove(entry)
                #     if locale_code == 'en':
                #         arrays.append(entry)

                entry.text = processText(entry.text)

            indent(transifexData)
            ElementTree.ElementTree(transifexData).write(resource_dir + "/" + resource_name, encoding="utf-8", method="xml")
            jsonData.close()

            # if locale_code == 'en':
            #     indent(arrays)
            #     ElementTree.ElementTree(arrays).write(resource_dir + "/string_arrays.xml", encoding="utf-8", method="xml")

        except ElementTree.ParseError as error:
            print("shit happens with " + currentFilePath)
            print(error)

pprint.pprint(totalCounter)
