"""Creates a csv file for every xml file in the root directory. 

The xml files can be created in JIRA from a serach and Export -> XML.

To run this just type python .\create_xray_csv.py and the csv's will be created"""

from bs4 import BeautifulSoup
import csv
import glob

default_status = "TO DO"
default_issue_type = "Test"

def parse_xml():

    for xml_file_name in glob.glob('*.xml'):

        with open(xml_file_name, 'r') as xml_file:
        
            xml_file_contents = xml_file.read()
            soup = BeautifulSoup(xml_file_contents, 'html.parser')
            results = []

            for item in soup.find_all('item'):
                row = {}
                
                key = item.key.get_text()
                row['key'] = item.key.get_text()
                row['status'] = default_status
                
                row['summary'] = item.summary.get_text()
                row['description'] = item.description.get_text()

                row['tests'] = ', '.join(
                    [link.get_text() for link in
                     item.find_all('issuekey') if
                     link.parent.parent.get('description') == 'tests'])
                     
                row['Environment'] = item.environment.get_text()

                row['features'] = ', '.join(
                    [custom_field.get_text() for custom_field in
                    item.customfields.find_all('customfieldvalues') if
                    custom_field.parent.customfieldname.get_text() == 'Features'])

                row['functional_areas'] = ', '.join(
                    [custom_field.get_text() for custom_field in
                    item.customfields.find_all('customfieldvalues') if
                    custom_field.parent.customfieldname.get_text() == 'Functional Areas'])

                row['reporter'] = item.reporter.get_text()
                row['priority'] = item.priority.get_text()
                
                row['attachments'] = ', '.join(
                    [attachment.get('name') for attachment in
                     item.find_all('attachment') if
                     attachment.get('name')])

                step_desc = []
                step_data = []
                step_results = []
                     
                #rather annoyingly we have ...steps.step.step
                for step in item.find_all('step'):
                    #only take the steps.step
                    if step.orderid:
                      step_desc.append(step.step.get_text())
                      step_data.append(step.data.get_text())
                      step_results.append(step.result.get_text())

                if len(step_desc) > 1:
                    row['step'] = step_desc[0]
                    row['data'] = step_data[0]
                    row['result'] = step_results[0]
                else:
                    row['step'] = ""
                    row['data'] = ""
                    row['result'] = ""

                results.append(row)
                
                for index, step in enumerate(step_desc):
                    if index > 0:
                        step_row = {}
                        step_row['key'] = key
                        step_row['step'] = step_desc[index]
                        step_row['data'] = step_data[index]
                        step_row['result'] = step_results[index]
                        step_row['status'] = ""
                        step_row['summary'] = ""
                        step_row['description'] = ""
                        step_row['tests'] = ""
                        step_row['Environment'] = ""
                        step_row['features'] = ""
                        step_row['functional_areas'] = ""
                        step_row['reporter'] = ""
                        step_row['priority'] = ""
                        step_row['attachments'] = ""
                        
                        results.append(step_row)
                
                    
            field_names = results[0].keys()
            
            output_order = ['key', 'status', 'summary', 'description', 'step', 'data', 'result', 'tests', 'Environment', 'features', 'functional_areas', 'reporter', 'priority', 'attachments']

            for column_name in reversed(output_order):
                field_names.remove(column_name)
                field_names.insert(0, column_name)            

            with open('{0}.csv'.format(
                    xml_file_name[:-len('.xml')]), 'wb') as csvfile:
                writer = csv.DictWriter(csvfile,
                                        delimiter=',',
                                        quotechar='"',
                                        quoting=csv.QUOTE_ALL,
                                        fieldnames=field_names)

                writer.writeheader()
                for result in results:
                    writer.writerow(result)

if __name__ == '__main__':
    parse_xml()
